package com.enigma.tokonyadia_api.service;

public interface RefreshTokenService {
    String generateRefreshToken(String userId);

    void deleteRefreshToken(String userId);

    String rotateRefreshToken(String userId);

    String getUserIdFromRefreshToken(String refreshToken);
}