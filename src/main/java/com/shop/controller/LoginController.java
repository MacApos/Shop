package com.shop.controller;

import com.shop.service.JwtTokenService;
import com.shop.service.UserService;
import com.shop.validation.group.Login;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.shop.entity.User;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public User login(@RequestBody @Validated(Login.class) User user, HttpServletResponse response) {
        jwtTokenService.authenticateUser(user, response);
        return userService.findByEmail(user.getEmail());
    }

    @GetMapping("/authenticated")
    public ResponseEntity<?> authenticatedAccess() {
        return ResponseEntity.ok("authenticated success");
    }

    @GetMapping("/user-access")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> userAccess() {
        return ResponseEntity.ok("user success");
    }

    @GetMapping("/admin-access")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> adminAccess() {
        return ResponseEntity.ok("admin success");

    }
}
