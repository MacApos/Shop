package com.shop.controller;

import com.shop.service.JwtTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shop.entity.User;

@RestController
@RequiredArgsConstructor
public class LoginController {

   private JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user, HttpServletResponse response) {
        User authenticatedUser = jwtTokenService.authenticateUser(user, response);
        return ResponseEntity.ok().body(authenticatedUser);
    }
}
