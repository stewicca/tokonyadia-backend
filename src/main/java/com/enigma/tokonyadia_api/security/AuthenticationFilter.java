package com.enigma.tokonyadia_api.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.tokonyadia_api.entity.User;
import com.enigma.tokonyadia_api.service.JwtService;
import com.enigma.tokonyadia_api.service.UserService;
import com.enigma.tokonyadia_api.util.LogUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (bearerToken != null && !jwtService.isTokenBlacklisted(bearerToken)) {
                DecodedJWT payload = jwtService.verifyToken(bearerToken);
                User user = userService.getOne(payload.getSubject());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}