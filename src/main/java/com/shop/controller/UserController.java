package com.shop.controller;

import com.shop.entity.RegistrationToken;
import com.shop.entity.User;
import com.shop.event.SendEmailEvent;
import com.shop.service.RegistrationTokenService;
import com.shop.service.UserService;
import com.shop.validator.groups.CheckFirst;
import com.shop.validator.groups.DefaultFirst;
import com.shop.validator.groups.AlreadyEnabled;
import com.shop.validator.groups.Exists;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RegistrationTokenService registrationTokenService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${react.origin}")
    private String origin;

    private void sendVerificationToken(User user, HttpServletRequest request) {
        RegistrationToken token = registrationTokenService.generateToken(user);
        SendEmailEvent userSendEmailEvent = new SendEmailEvent(
                user.getEmail(),
                "registration.confirm.subject",
                "registration-confirm.html",
                request.getLocale(),
                Map.of("user", user,
                        "url", String.format("%sconfirm-registration?token=%s", origin, token)));
        eventPublisher.publishEvent(userSendEmailEvent);
    }

    @PostMapping("/create")
    public User createUser(@RequestBody @Validated(DefaultFirst.class) User user, HttpServletRequest request) {
        userService.save(user);
        sendVerificationToken(user, request);
        return user;
    }

    @GetMapping("/confirm-registration")
    public User confirmRegistration(@Validated(Exists.class) RegistrationToken registrationToken)
            throws BindException {
        RegistrationToken checkedToken = registrationTokenService.validateToken(registrationToken.getToken());
        User user = checkedToken.getUser();
        userService.enableUser(user);
        return user;
    }

    @GetMapping("/resend-verification-token")
    public User resendVerificationToken(@Validated({Exists.class, AlreadyEnabled.class}) User user,
                                        HttpServletRequest request) {
        sendVerificationToken(user, request);
        return user;
    }

}