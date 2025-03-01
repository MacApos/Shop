package com.shop.controller;

import com.shop.entity.*;
import com.shop.event.EmailEvent;
import com.shop.service.*;
import com.shop.validation.user.group.expensive.ResetPassword;
import com.shop.validation.user.group.sequence.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    private void sendTokenEmail(String to, String subjectCode, String template, Map<String, Object> variables) {
        EmailEvent emailEvent = new EmailEvent(to, subjectCode, template, variables);
        eventPublisher.publishEvent(emailEvent);
    }

    private void sendTokenEmail(User user, String subjectCode, String template, String url) {
        RegistrationToken registrationToken = registrationTokenService.generateAndSaveToken(user);
        Map<String, Object> variables = Map.of(
                "user", user,
                "url", origin + url + registrationToken.getToken());
        EmailEvent emailEvent = new EmailEvent(user.getEmail(), subjectCode, template, variables);
        eventPublisher.publishEvent(emailEvent);
    }

    @PostMapping("/create")
    public User createUser(@RequestBody @Validated(CreateUserSequence.class) User user) {
        userService.save(user);
        roleService.save(new Role(RoleEnum.ROLE_USER, user));
        sendTokenEmail(user,
                "confirm.registration.subject",
                "confirm-registration.html",
                "confirm-registration?token=");
        return new User();
    }

    @GetMapping("/confirm-registration")
    public ResponseEntity<?> confirmRegistration(@Validated(UserExistsSequence.class) RegistrationToken token,
                                                 HttpServletResponse response) {
        RegistrationToken validatedToken = registrationTokenService.validateToken(token);
        User user = validatedToken.getUser();
        userService.save(user);
        jwtTokenService.authWithoutPassword(user, response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/resend-registration-token")
    public ResponseEntity<?> resendRegistrationToken(@Validated(UserExistsSequence.class) RegistrationToken token) {
        RegistrationToken existingToken = registrationTokenService.findByToken(token.getToken());
        User user = existingToken.getUser();
        sendTokenEmail(user,
                "confirm.registration.subject",
                "confirm-registration.html",
                "confirm-registration?token=");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/send-reset-password-token")
    public ResponseEntity<?> sendResetPasswordToken(@RequestBody @Validated(UserExistsSequence.class) User user) {
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isEnabled()) {
            sendTokenEmail(existingUser,
                    "reset.password.subject",
                    "reset-password.html",
                    "reset-password?token=");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public User resetPassword(@Validated(UserExistsSequence.class) RegistrationToken token,
                              @RequestBody @Validated(ResetPassword.class) User user) {
        RegistrationToken validatedToken = registrationTokenService.validateToken(token);
        User existingUser = validatedToken.getUser();
        user.setPassword(user.getPassword());
        userService.save(existingUser);
        return existingUser;
    }

    @PostMapping("/update-password")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public User updatePassword(@RequestBody @Validated(UpdatePasswordSequence.class) User user) {
        User existingUser = userService.findByEmail(user.getEmail());
        userService.update(user, existingUser);
        return existingUser;
    }

    @PostMapping("/update-email")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public User updateEmail(@RequestBody @Validated(UpdateEmailSequence.class) User user) {
        String email = user.getEmail();
        String newEmail = user.getNewEmail();
        User existingUser = userService.findByEmail(email);
        existingUser.setNewEmail(newEmail);
        userService.save(existingUser);

        Map<String, Object> variables = new HashMap<>(Map.of("user", user));
        sendTokenEmail(email, "oldEmail", "oldEmailTemplate", variables);

        RegistrationToken registrationToken = registrationTokenService.generateAndSaveToken(existingUser);
        variables.put("url", origin + "url" + registrationToken.getToken());
        sendTokenEmail(email, "newEmail", "newEmailTemplate", variables);

        return user;
    }

    @GetMapping("/confirm-update-email")
    public ResponseEntity<?> confirmUpdateEmail(@Validated(UserExistsSequence.class) RegistrationToken token) {
        RegistrationToken registrationToken = registrationTokenService.validateToken(token);
        User user = registrationToken.getUser();
        user.setEmail(user.getNewEmail());
        user.setNewEmail(null);
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public User update(@RequestBody @Validated(UpdateUserSequence.class) User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        userService.update(user, existingUser);
        return existingUser;
    }
}