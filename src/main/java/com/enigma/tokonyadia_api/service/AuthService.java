package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.dto.req.AuthReq;
import com.enigma.tokonyadia_api.dto.res.AuthRes;
import com.enigma.tokonyadia_api.dto.res.CustomerRes;

public interface AuthService {
    CustomerRes register(AuthReq req);

    AuthRes login(AuthReq req);

    void logout(String bearerToken);

    AuthRes refreshToken(String refreshToken);
}