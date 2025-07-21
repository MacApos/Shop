package com.shop.controller;

import com.shop.model.*;
import com.shop.service.*;
import com.shop.validation.user.group.expensive.ResetPassword;
import com.shop.validation.user.group.sequence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final RegistrationTokenService registrationTokenService;
    private final AuthenticationService authenticationService;

    //    @Value("${react.origin}")
    @Value(value = "http://localhost:8080")
    private String origin;

    @GetMapping("/send-reset-password-token")
    public ResponseEntity<?> sendResetPasswordToken(@RequestBody @Validated(UserExistsSequence.class) User user) {
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isEnabled()) {
            emailService.sendTokenEmail(existingUser,
                    "reset.password.subject",
                    "reset-password.html",
                    "/reset-password?token=");
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
    @PreAuthorize("hasRole('ROLE_USER')")
    public User updatePassword(@RequestBody @Validated(UpdatePasswordSequence.class) User user) {
        User existingUser = userService.findByEmail(user.getEmail());
        userService.update(user, existingUser);
        return existingUser;
    }

    @PostMapping("/update-email")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User updateEmail(@RequestBody @Validated(UpdateEmailSequence.class) User user) {
        String email = user.getEmail();
        String newEmail = user.getNewEmail();
        User existingUser = userService.findByEmail(email);
        existingUser.setNewEmail(newEmail);
        userService.save(existingUser);

        Map<String, Object> variables = new HashMap<>(Map.of("user", user));
        emailService.sendTokenEmail(email, "oldEmail", "oldEmailTemplate", variables);

        RegistrationToken registrationToken = registrationTokenService.generateAndSaveToken(existingUser);
        variables.put("url", origin + "url" + registrationToken.getToken());
        emailService.sendTokenEmail(email, "newEmail", "newEmailTemplate", variables);

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
    @PreAuthorize("hasRole('ROLE_USER')")
    public User update(@RequestBody @Validated(UpdateUserSequence.class) User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        userService.update(user, existingUser);
        return existingUser;
    }
}