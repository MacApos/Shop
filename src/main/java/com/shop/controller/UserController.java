package com.shop.controller;

import com.shop.entity.User;
import com.shop.event.RegistrationEvent;
import com.shop.service.EmailService;
import com.shop.service.JwtTokenService;
import com.shop.service.LocaleService;
import com.shop.service.UserService;
import com.shop.validator.groups.DefaultFirst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final JwtTokenService jwtTokenService;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping("/create")
    public User createUser(@RequestBody @Validated({DefaultFirst.class}) User user, HttpServletRequest request) {
//        userService.save(user);
        eventPublisher.publishEvent(new RegistrationEvent(request.getRequestURL().toString(),
                request.getLocale(), user));
        return user;
    }

    @GetMapping("/confirm-registration")
    public String confirmRegistration(@RequestParam String token) {
        System.out.println(token);
        return "";
    }

}