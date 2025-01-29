package com.shop.controller;

import com.shop.entity.RegistrationToken;
import com.shop.entity.User;
import com.shop.event.RegistrationEvent;
import com.shop.service.RegistrationTokenService;
import com.shop.service.UserService;
import com.shop.validator.groups.CheckInOrder;
import com.shop.validator.groups.DefaultFirst;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RegistrationTokenService registrationTokenService;
    private final ApplicationEventPublisher eventPublisher;
    private Validator validator;

    @GetMapping("/ok")
    public String ok() {
        return "ok";
    }

    @PostMapping("/create")
    public User createUser(@RequestBody @Validated(DefaultFirst.class) User user, HttpServletRequest request) {
        userService.save(user);
        String url = request.getRequestURL().toString();
        Locale locale = request.getLocale();
        eventPublisher.publishEvent(new RegistrationEvent(url, locale, user));
        return user;
    }

    @GetMapping("/confirm-registration")
    public String confirmRegistration(@Validated(CheckInOrder.class) RegistrationToken registrationToken) throws BindException {
        registrationTokenService.validateToken(registrationToken.getToken());
        return "ok";

    }

}