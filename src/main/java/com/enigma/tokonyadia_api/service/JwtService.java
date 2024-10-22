package com.enigma.tokonyadia_api.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.tokonyadia_api.entity.User;

public interface JwtService {
    String generateToken(User user);

    DecodedJWT verifyToken(String bearerToken);

    void blacklistAccessToken(String bearerToken);

    boolean isTokenBlacklisted(String bearerToken);
}