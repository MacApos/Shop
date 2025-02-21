package com.shop.service;

import com.shop.entity.Role;
import com.shop.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final AuthenticationManager authManager;
    private final JwtEncoder jwtEncoder;
    private final Long expiry = 3600L;
    private final RoleService roleService;

    private String createToken(Authentication authentication) {
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

    public void authenticateUser(User user, HttpServletResponse response) {
        String email = user.getEmail();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authManager.authenticate(authenticationToken);

        String token = createToken(authentication);
        setJwtAuthorizationCookie(response, token);
    }

    public void authWithoutPassword(User user, HttpServletResponse response) {
        String email = user.getEmail();
        List<Role> roles = roleService.findByUser(user);
        List<GrantedAuthority> authorities = roles
                .stream()
                .distinct()
                .map(r -> new SimpleGrantedAuthority(r.getName().name()))
                .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = createToken(authentication);
        setJwtAuthorizationCookie(response, token);
    }
}
