package com.shop.controller;

import com.shop.service.AuthenticationService;
import com.shop.service.JwtTokenService;
import com.shop.service.UserService;
import com.shop.validation.user.group.defaults.DefaultPassword;
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
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public User login(@RequestBody @Validated(DefaultPassword.class) User user, HttpServletResponse response) {
        jwtTokenService.authenticateUser(user, response);
        return userService.findByEmail(user.getEmail());
    }

    @GetMapping("/user-access")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> userAccess() {
        authenticationService.getAuthenticatedUser();
        return ResponseEntity.ok("user success");
    }

    @GetMapping("/admin-access")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> adminAccess() {
        return ResponseEntity.ok("admin success");

    }
}
