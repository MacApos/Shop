package com.shop.controller;

import com.shop.model.RegistrationToken;
import com.shop.model.Role;
import com.shop.model.RoleEnum;
import com.shop.model.User;
import com.shop.service.*;
import com.shop.validation.user.group.sequence.CreateUserSequence;
import com.shop.validation.user.group.sequence.UserExistsSequence;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;
    private final RoleService roleService;
    private final EmailService emailService;
    private final RegistrationTokenService registrationTokenService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody @Validated(CreateUserSequence.class) User user) {
        userService.save(user);
        roleService.save(new Role(RoleEnum.ROLE_USER, user));
        emailService.sendTokenEmail(user,
                "confirm.registration.subject",
                "confirm-registration.html",
                "/confirm-registration?token=");
        return ResponseEntity.ok(user);
    }

    @GetMapping("/confirm-registration")
    public ResponseEntity<User> confirmRegistration(@Validated(UserExistsSequence.class)
                                                                   RegistrationToken token,
                                                                   HttpServletResponse response) {
        RegistrationToken validatedToken = registrationTokenService.validateToken(token);
        User user = validatedToken.getUser();
        user.setEnabled(true);
        userService.save(user);
        jwtTokenService.authenticateWithoutPassword(user, response);
        return ResponseEntity.ok(userService.findByEmail(user.getEmail()));
    }

    @GetMapping("/resend-registration-token")
    public ResponseEntity<User> resendRegistrationToken(@Validated(UserExistsSequence.class) RegistrationToken token) {
        RegistrationToken existingToken = registrationTokenService.findByToken(token.getToken());
        existingToken.setActive(false);
        User user = existingToken.getUser();
        emailService.sendTokenEmail(user,
                "confirm.registration.subject",
                "confirm-registration.html",
                "/confirm-registration?token=");
        return ResponseEntity.ok(user);
    }


}