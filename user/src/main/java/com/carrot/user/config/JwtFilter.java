package com.carrot.user.config;

import com.carrot.user.jwt.JwtAuthenticationProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = jwtAuthenticationProvider.resolveAccessToken(request);
        if (accessToken != null && jwtAuthenticationProvider.validateToken(accessToken)) {
            Claims claims = jwtAuthenticationProvider.getClaims(accessToken);
            Long userId = claims.get("userId", Long.class);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Authentication authentication = jwtAuthenticationProvider.getAuthentication(userId);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}