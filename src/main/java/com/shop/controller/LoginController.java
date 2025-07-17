package com.shop.controller;

import com.shop.service.AuthenticationService;
import com.shop.service.JwtTokenService;
import com.shop.service.UserService;
import com.shop.validation.user.group.defaults.DefaultPassword;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.shop.entity.User;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Validated(DefaultPassword.class) User user,
                                                     HttpServletResponse response) {
        return ResponseEntity.ok(Map.of("jwt", jwtTokenService.authenticateUser(user, response))
        );
    }

    @GetMapping("/user-access")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> userAccess() {
        return ResponseEntity.ok(Map.of("success", "user success"));
    }

    @GetMapping("/admin-access")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> adminAccess() {
        return ResponseEntity.ok(Map.of("success", "admin success"));
    }

}
