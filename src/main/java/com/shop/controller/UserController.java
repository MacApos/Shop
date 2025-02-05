package com.shop.controller;

import com.shop.entity.*;
import com.shop.event.SendEmailEvent;
import com.shop.service.JwtTokenService;
import com.shop.service.RegistrationTokenService;
import com.shop.service.RoleService;
import com.shop.service.UserService;
import com.shop.validation.group.defaultFirst.DefaultAndAlreadyExists;
import com.shop.validation.group.Exists;
import com.shop.validation.group.defaultFirst.DefaultAndExists;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private void sendRegistrationToken(User user, HttpServletRequest request) {
        RegistrationToken registrationToken = registrationTokenService.generateAndSaveToken(user);
        SendEmailEvent userSendEmailEvent = new SendEmailEvent(
                user.getEmail(),
                "registration.confirm.subject",
                "registration-confirm.html",
                request.getLocale(),
                Map.of("user", user,
                        "url", String.format("%sconfirm-registration?token=%s", origin,
                                registrationToken.getToken())));
        eventPublisher.publishEvent(userSendEmailEvent);
    }

    @PostMapping("/create")
    public User createUser(@RequestBody @Validated(DefaultAndAlreadyExists.class) User user,
                           HttpServletRequest request) {
        userService.save(user);
        roleService.save(new Role(RoleEnum.ROLE_USER, user));
        sendRegistrationToken(user, request);
        return user;
    }

    @GetMapping("/confirm-registration")
    public User confirmRegistration(@Validated(Exists.class) RegistrationToken token,
                                    HttpServletResponse response) throws BindException {
        RegistrationToken validatedToken = registrationTokenService.validateToken(token);
        User user = validatedToken.getUser();
        user.setEnabled(true);
        userService.save(user);
        jwtTokenService.authWithoutPassword(user, response);
        return user;
    }

    @GetMapping("/resend-registration-token")
    public User resendRegistrationTokenByUsername(@Validated(Exists.class) RegistrationToken token,
                                                  HttpServletRequest request) {
        RegistrationToken existingToken = registrationTokenService.findByToken(token.getToken());
        User user = existingToken.getUser();
        sendRegistrationToken(user, request);
        return user;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/reset-password")
    public User resetPassword(@RequestBody @Validated(DefaultAndExists.class) User user,
                              HttpServletRequest request) {
        User existingUser = userService.findByUsername(user.getUsername());
        RegistrationToken registrationToken = registrationTokenService.generateAndSaveToken(existingUser);
        SendEmailEvent userSendEmailEvent = new SendEmailEvent(
                user.getEmail(),
                "registration.confirm.subject",
                "registration-confirm.html",
                request.getLocale(),
                Map.of("user", user,
                        "url", String.format("%sconfirm-registration?token=%s", origin,
                                registrationToken.getToken())));
        eventPublisher.publishEvent(userSendEmailEvent);
        return user;
    }


//    @PreAuthorize("hasRole('ROLE_USER')")
//    @GetMapping("/reset-password")
//    public User resetPassword2(@Validated({Exists.class}) RegistrationToken token) {
//        User user = userService.findByUsername(username);
//        user.setPassword(password.getPassword());
//        userService.save(user);
//        return user;
//    }

}