package com.shop.controller;

import com.shop.entity.RegistrationToken;
import com.shop.entity.User;
import com.shop.event.SendEmailEvent;
import com.shop.service.JwtTokenService;
import com.shop.service.RegistrationTokenService;
import com.shop.service.UserService;
import com.shop.validation.groups.DefaultFirst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final JwtTokenService jwtTokenService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${react.origin}")
    private String origin;

    private void sendRegistrationToken(User user, HttpServletRequest request) {
        RegistrationToken token = registrationTokenService.generateAndSaveToken(user);
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
        sendRegistrationToken(user, request);
        return user;
    }

    @GetMapping("/resend-registration-token")
    public User resendRegistrationToken(@RequestParam
//                                            @Validated(Exists.class)
                                            RegistrationToken registrationToken,
                                        HttpServletRequest request) throws BindException {
        registrationTokenService.validateToken(registrationToken);
        RegistrationToken checkedToken = registrationTokenService.findByToken(registrationToken.getToken());
        registrationTokenService.validateToken(checkedToken);
        User user = checkedToken.getUser();
        sendRegistrationToken(user, request);
        return user;
    }

    @GetMapping("/confirm-registration")
    public User confirmRegistration(@RequestParam
//                                        @Validated(Exists.class)
                                        RegistrationToken registrationToken,
                                    HttpServletResponse response) throws BindException {
        RegistrationToken checkedToken = registrationTokenService.findByToken(registrationToken.getToken());
        registrationTokenService.validateToken(checkedToken);
        User user = checkedToken.getUser();
        user.setEnabled(true);
        userService.save(user);
        jwtTokenService.authenticateUser(user, response);
        return user;
    }

}