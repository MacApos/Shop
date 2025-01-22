package com.shop.controller;

import com.shop.entity.User;
import com.shop.service.EmailService;
import com.shop.service.UserService;
import com.shop.validator.groups.DefaultFirst;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/create")
    public User createUser(@RequestBody @Validated({DefaultFirst.class}) User user) {
        userService.save(user);
        emailService.sendHtmlMessage("u1326546@gmail.com", "sibject", "<p>Howdy</p>");
        return user;
    }
}