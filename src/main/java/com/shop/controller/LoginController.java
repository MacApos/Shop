package com.shop.controller;

import com.shop.service.JwtTokenService;
import com.shop.service.UserService;
import com.shop.validation.groups.Login;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.shop.entity.User;

@RestController
@RequiredArgsConstructor
public class LoginController {

   private final UserService userService;
   private final JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public User login(@RequestBody @Validated({Login.class}) User user, HttpServletResponse response) {
        jwtTokenService.authenticateUser(user, response);
        return userService.findByEmail(user.getEmail());
    }
}
