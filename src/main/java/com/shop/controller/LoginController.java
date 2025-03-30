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
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Validated(DefaultPassword.class) User user) {
        return ResponseEntity.ok(Map.of(
                "user", userService.findByEmail(user.getEmail()),
                "jwt", jwtTokenService.authenticateUser(user))
        );
    }

    @GetMapping("/user-access")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> userAccess() {
        authenticationService.getAuthenticatedUser();
        return ResponseEntity.ok("user success");
    }

    @GetMapping("/admin-access")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> adminAccess() {
        ResponseEntity<Map<String, String>> ok = ResponseEntity.ok(Map.of("success", "admin success"));
        return ok;
    }

}
