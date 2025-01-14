package com.shop.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import com.shop.entity.User;
import com.shop.service.UserService;

import java.time.Instant;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class LoginController {
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authManager;
    private final UserService userService;
    private final Long expiry = 30L;


    public String createToken(Authentication authentication) {
        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private void setJwtAuthorizationCookie(final HttpServletResponse response, String token) {
        String cookieKey = "jwt";
        Cookie cookie = new Cookie(cookieKey, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(expiry.intValue());
        response.addCookie(cookie);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user, HttpServletResponse response) {
        String email = user.getEmail();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authManager.authenticate(authenticationToken);
        User authenticatedUser = userService.findByEmail(email);

        String token = createToken(authentication);
        setJwtAuthorizationCookie(response, token);

        return ResponseEntity.ok().body(authenticatedUser);
    }
}
