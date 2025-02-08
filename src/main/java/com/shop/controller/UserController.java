package com.shop.controller;

import com.shop.entity.*;
import com.shop.event.EmailEvent;
import com.shop.service.*;
import com.shop.validation.group.Create;
import com.shop.validation.group.Exists;
import com.shop.validation.group.ResetPassword;
import com.shop.validation.group.defaultFirst.DefaultAndCreateOrUpdate;
import com.shop.validation.group.defaultFirst.DefaultAndExists;
import com.shop.validation.group.defaultFirst.DefaultAndResetPassword;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final JwtTokenService jwtTokenService;
    private final RegistrationTokenService registrationTokenService;
    private final ApplicationEventPublisher eventPublisher;
    private final MessageService messageService;

    @Value("${react.origin}")
    private String origin;


    private void sendTokenEmail(User user, Locale locale, String subjectCode, String template, String url,
                                Map<String, Object> variables) {
        RegistrationToken registrationToken = registrationTokenService.generateAndSaveToken(user);
        variables.put("url", origin + url + registrationToken.getToken());
        EmailEvent emailEvent = new EmailEvent(user.getEmail(), subjectCode, template, locale, variables);
        eventPublisher.publishEvent(emailEvent);
    }

    private void sendRegistrationTokenEmail(User user, Locale locale) {
        sendTokenEmail(user, locale,
                "confirm.registration.subject",
                "confirm-registration.html",
                "confirm-registration?token=",
                Map.of("user", user));
    }

    @PostMapping("/create")
    public User createUser(@RequestBody @Validated(DefaultAndCreateOrUpdate.class) User user,
                           Locale locale) {
        userService.save(user);
        roleService.save(new Role(RoleEnum.ROLE_USER, user));
        sendRegistrationTokenEmail(user, locale);
        return new User();
    }

    @GetMapping("/confirm-registration")
    public ResponseEntity<?> confirmRegistration(@Validated(Exists.class) RegistrationToken token,
                                                 HttpServletResponse response) throws BindException {
        RegistrationToken validatedToken = registrationTokenService.validateToken(token);
        User user = validatedToken.getUser();
        user.setEnabled(true);
        userService.save(user);
        jwtTokenService.authWithoutPassword(user, response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/resend-registration-token")
    public ResponseEntity<?> resendRegistrationToken(@Validated(Exists.class) RegistrationToken token,
                                                     Locale locale) {
        RegistrationToken existingToken = registrationTokenService.findByToken(token.getToken());
        User user = existingToken.getUser();
        sendRegistrationTokenEmail(user, locale);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/send-reset-password-token")
    public ResponseEntity<?> sendResetPasswordToken(@RequestBody @Validated(Exists.class) User user,
                                                    Locale locale) {
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isEnabled()) {
            sendTokenEmail(user, locale,
                    "reset.password.subject",
                    "reset-password.html",
                    "reset-password?token=",
                    Map.of("user", user));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-reset-password-token")
    public ResponseEntity<?> checkResetPasswordToken(@Validated(Exists.class) RegistrationToken token)
            throws BindException {
        registrationTokenService.validateToken(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public User resetPassword(@Validated(Exists.class) RegistrationToken token,
                              @RequestBody @Validated(ResetPassword.class)
                              User user) throws BindException {
        RegistrationToken validatedToken = registrationTokenService.validateToken(token);
        User existingUser = validatedToken.getUser();
        user.setPassword(user.getPassword());
        userService.save(existingUser);
        return existingUser;
    }
}