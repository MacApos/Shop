package com.shop.controller;

import com.shop.entity.*;
import com.shop.event.EmailEvent;
import com.shop.service.JwtTokenService;
import com.shop.service.RegistrationTokenService;
import com.shop.service.RoleService;
import com.shop.service.UserService;
import com.shop.validation.group.ResetPassword;
import com.shop.validation.group.defaultFirst.DefaultAndAlreadyExists;
import com.shop.validation.group.Exists;
import com.shop.validation.group.defaultFirst.DefaultAndExists;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Value("${react.origin}")
    private String origin;


    private void sendTokenEmail(User user, HttpServletRequest request, String subjectCode, String template, String url,
                                Map<String, Object> variables) {
        RegistrationToken registrationToken = registrationTokenService.generateAndSaveToken(user);
        variables.put("url", origin + url + registrationToken.getToken());
        EmailEvent emailEvent = new EmailEvent(user.getEmail(), subjectCode, template, request.getLocale(),
                variables);
        eventPublisher.publishEvent(emailEvent);
    }

    private void sendRegistrationTokenEmail(User user, HttpServletRequest request) {
        sendTokenEmail(user, request,
                "confirm.registration.subject",
                "confirm-registration.html",
                "confirm-registration?token=",
                Map.of("user", user));
    }

    @PostMapping("/create")
    public User createUser(@RequestBody @Validated(DefaultAndAlreadyExists.class) User user,
                           HttpServletRequest request) {
        userService.save(user);
        roleService.save(new Role(RoleEnum.ROLE_USER, user));
        sendRegistrationTokenEmail(user, request);
        return user;
    }

    @GetMapping("/confirm-registration")
    public ResponseEntity<?> confirmRegistration(@Validated(DefaultAndExists.class) RegistrationToken token,
                                                 HttpServletResponse response) throws BindException {
        RegistrationToken validatedToken = registrationTokenService.validateToken(token);
        User user = validatedToken.getUser();
        user.setEnabled(true);
        userService.save(user);
        jwtTokenService.authWithoutPassword(user, response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/resend-registration-token")
    public ResponseEntity<?> resendRegistrationToken(@Validated(DefaultAndExists.class) RegistrationToken token,
                                                     HttpServletRequest request) {
        RegistrationToken existingToken = registrationTokenService.findByToken(token.getToken());
        User user = existingToken.getUser();
        sendRegistrationTokenEmail(user, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/send-reset-password-token")
    public ResponseEntity<?> sendResetPasswordToken(@RequestBody @Validated(DefaultAndExists.class) User user,
                                                    HttpServletRequest request) {
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isEnabled()) {
            sendTokenEmail(user, request,
                    "reset.password.subject",
                    "reset-password.html",
                    "reset-password?token=",
                    Map.of("user", user));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-reset-password-token")
    public ResponseEntity<?> checkResetPasswordToken(@Validated(DefaultAndExists.class) RegistrationToken token)
            throws BindException {
        registrationTokenService.validateToken(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public User resetPassword(@Validated(DefaultAndExists.class) RegistrationToken token,
                              @RequestBody @Validated Password password) throws BindException {
        RegistrationToken validatedToken = registrationTokenService.validateToken(token);
        User user = validatedToken.getUser();
        user.setPassword(password.getPassword());
        userService.save(user);
        return user;
    }
}