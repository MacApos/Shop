package com.shop.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        MutableHttpServerRequest mutableHttpServerRequest = new MutableHttpServerRequest(request);

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase("jwt")) {
                    mutableHttpServerRequest.putHeader("Authorization", "Bearer " + cookie.getValue());
                }
            }
        }

        filterChain.doFilter(mutableHttpServerRequest, response);
    }
}
