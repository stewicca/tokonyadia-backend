package com.enigma.tokonyadia_api.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.entity.User;
import com.enigma.tokonyadia_api.service.JwtService;
import com.enigma.tokonyadia_api.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {
    private final RedisService redisService;

    private final String BLACKLISTED = "BLACKLISTED";

    @Value("${app.tokonyadia.jwt-secret}")
    private String JWT_SECRET;

    @Value("${app.tokonyadia.jwt-issuer}")
    private String JWT_ISSUER;

    @Value("${app.tokonyadia.jwt-expiration-in-seconds}")
    private Integer JWT_EXPIRATION_IN_SECONDS;

    @Override
    public String generateToken(User user) {
        try {
            return JWT.create()
                    .withSubject(user.getId())
                    .withClaim("role", user.getRole().getName())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plus(JWT_EXPIRATION_IN_SECONDS, ChronoUnit.SECONDS))
                    .withIssuer(JWT_ISSUER)
                    .sign(Algorithm.HMAC256(JWT_SECRET));
        } catch (JWTCreationException e) {
            throw new RuntimeException(Constant.ERROR_GENERATE_TOKEN_MSG);
        }
    }

    @Override
    public DecodedJWT verifyToken(String bearerToken) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET))
                    .withIssuer(JWT_ISSUER)
                    .build();
            return verifier.verify(parseToken(bearerToken));
        } catch (JWTVerificationException e) {
            throw new RuntimeException(Constant.ERROR_VERIFY_TOKEN_MSG);
        }
    }

    @Override
    public void blacklistAccessToken(String bearerToken) {
        DecodedJWT decodedJWT = verifyToken(bearerToken);
        Date expiresAt = decodedJWT.getExpiresAt();
        long timeLeft = expiresAt.getTime() - System.currentTimeMillis();
        redisService.save(parseToken(bearerToken), BLACKLISTED, Duration.ofMillis(timeLeft));
    }

    @Override
    public boolean isTokenBlacklisted(String bearerToken) {
        String blacklistedToken = redisService.get(parseToken(bearerToken));
        return blacklistedToken != null && blacklistedToken.equals(BLACKLISTED);
    }

    private String parseToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer")) return bearerToken.substring(7);
        return null;
    }
}