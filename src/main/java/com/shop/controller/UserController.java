package com.shop.controller;

import com.shop.model.*;
import com.shop.service.*;
import com.shop.validation.user.group.expensive.ResetPassword;
import com.shop.validation.user.group.sequence.*;
import jakarta.servlet.http.HttpServletRequest;
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
    private final EmailService emailService;
    private final JwtTokenService jwtTokenService;
    private final RegistrationTokenService registrationTokenService;
    private final ApplicationEventPublisher eventPublisher;
    private final MessageService messageService;
    private final AuthenticationService authenticationService;

    @Value("${react.origin}")
    private String origin;

    @GetMapping
    public ResponseEntity<User> getAuthenticatedUser(HttpServletRequest request) {
        User authenticatedUser = authenticationService.getAuthenticatedUser();
        return ResponseEntity.ok(authenticatedUser);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody @Validated(CreateUserSequence.class) User user) {
        userService.save(user);
        roleService.save(new Role(RoleEnum.ROLE_USER, user));
        emailService.sendTokenEmail(user,
                "confirm.registration.subject",
                "registration-confirm.html",
                "registration-confirm?token=");
        return ResponseEntity.ok(user);
    }

    @GetMapping("/confirm-registration")
    public ResponseEntity<Map<String, Object>> confirmRegistration(@Validated(UserExistsSequence.class)
                                                                   RegistrationToken token) {
        RegistrationToken validatedToken = registrationTokenService.validateToken(token);
        User user = validatedToken.getUser();
        user.setEnabled(true);
        userService.save(user);
        jwtTokenService.authenticateWithoutPassword(user);
        return ResponseEntity.ok(Map.of(
                "user", userService.findByEmail(user.getEmail()),
                "jwt", jwtTokenService.authenticateWithoutPassword(user))
        );
    }

    @GetMapping("/resend-registration-token")
    public ResponseEntity<User> resendRegistrationToken(@Validated(UserExistsSequence.class) RegistrationToken token) {
        RegistrationToken existingToken = registrationTokenService.findByToken(token.getToken());
        existingToken.setActive(false);
        User user = existingToken.getUser();
        emailService.sendTokenEmail(user,
                "confirm.registration.subject",
                "registration-confirm.html",
                "registration-confirm?token=");
        return ResponseEntity.ok(user);
    }

    @GetMapping("/send-reset-password-token")
    public ResponseEntity<?> sendResetPasswordToken(@RequestBody @Validated(UserExistsSequence.class) User user) {
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isEnabled()) {
            emailService.sendTokenEmail(existingUser,
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
//    @PreAuthorize("hasRole('ROLE_USER')")
    public User update(@RequestBody @Validated(UpdateUserSequence.class) User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        userService.update(user, existingUser);
        return existingUser;
    }
}