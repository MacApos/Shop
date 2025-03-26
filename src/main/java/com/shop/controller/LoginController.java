package com.shop.controller;

import com.shop.service.AuthenticationService;
import com.shop.service.JwtTokenService;
import com.shop.service.UserService;
import com.shop.validation.user.group.defaults.DefaultPassword;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.shop.entity.User;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Validated(DefaultPassword.class) User user, HttpServletRequest request, HttpServletResponse response) {
//        jwtTokenService.authenticateUser(user, response);
        Cookie cookie = new Cookie("jwt", "your-jwt-token");
        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setAttribute("SameSite", "None");
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok(userService.findByEmail(user.getEmail()));
    }

    @PostMapping("/secrete-token")
    public ResponseEntity<Map<String, Object>> createToken(@RequestBody @Validated(DefaultPassword.class) User user) {
        return ResponseEntity.ok(Map.of(
                "user", userService.findByEmail(user.getEmail()),
                "token", jwtTokenService.authenticateUser(user))
        );
    }

    @PostMapping("/login2")
    public void login2(@RequestBody @Validated(DefaultPassword.class) User user, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt2", "your-jwt-token");
        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setAttribute("SameSite", "None");
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
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

    @GetMapping("/set-cookie")
    public Map<String, String> setCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("authToken", "your-jwt-token");
        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setAttribute("SameSite", "None");
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);

        ResponseCookie build = ResponseCookie.from("authToken", "your-jwt-token")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("None")
                .maxAge(7 * 24 * 60 * 60)
                .build();
        response.addCookie(cookie);
        return Map.of("cookie", cookie.toString());
    }
}
