package com.enigma.tokonyadia_api.controller;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.AuthReq;
import com.enigma.tokonyadia_api.dto.res.AuthRes;
import com.enigma.tokonyadia_api.dto.res.CustomerRes;
import com.enigma.tokonyadia_api.dto.res.Res;
import com.enigma.tokonyadia_api.service.AuthService;
import com.enigma.tokonyadia_api.util.ResUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Tag(name = "Authentication")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = Constant.AUTH_API_URL)
public class AuthController {
    private final AuthService authService;

    @Value("${app.tokonyadia.refresh-token-expiration-in-seconds}")
    private Integer REFRESH_TOKEN_EXPIRATION_IN_SECONDS;

    private static class SwagRegRes extends Res<CustomerRes> {
    }

    private static class SwagAuthRes extends Res<AuthRes> {
    }

    @Operation(
            summary = "Register",
            responses = {
                    @ApiResponse(responseCode = "200", description = Constant.SUCCESS_REGISTER_MSG, content = @Content(schema = @Schema(implementation = SwagRegRes.class))),
            }
    )
    @PostMapping(path = "/signup")
    public ResponseEntity<?> register(@RequestBody AuthReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_REGISTER_MSG,
                authService.register(req)
        );
    }

    @Operation(
            summary = "Login",
            responses = {
                    @ApiResponse(responseCode = "200", description = Constant.SUCCESS_LOGIN_MSG, content = @Content(schema = @Schema(implementation = SwagAuthRes.class))),
            }
    )
    @PostMapping(path = "/signin")
    public ResponseEntity<?> login(@RequestBody AuthReq req, HttpServletResponse res) {
        AuthRes authRes = authService.login(req);
        setCookie(res, authRes.getRefreshToken());
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_LOGIN_MSG,
                authRes
        );
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Logout",
            responses = {
                    @ApiResponse(responseCode = "200", description = Constant.SUCCESS_LOGOUT_MSG, content = @Content(schema = @Schema(implementation = Res.class))),
            }
    )
    @PostMapping(path = "/signout")
    public ResponseEntity<?> logout(HttpServletRequest req) {
        String bearerToken = req.getHeader(HttpHeaders.AUTHORIZATION);
        authService.logout(bearerToken);
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_LOGOUT_MSG,
                null
        );
    }

    @Operation(
            summary = "Refresh Token",
            responses = {
                    @ApiResponse(responseCode = "200", description = Constant.SUCCESS_REFRESH_TOKEN_MSG, content = @Content(schema = @Schema(implementation = SwagAuthRes.class))),
            }
    )
    @PostMapping(path = "/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest req, HttpServletResponse res) {
        String refreshToken = getRefreshTokenFromCookie(req);
        AuthRes authRes = authService.refreshToken(refreshToken);
        setCookie(res, authRes.getRefreshToken());
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_REFRESH_TOKEN_MSG,
                authRes
        );
    }

    private String getRefreshTokenFromCookie(HttpServletRequest req) {
        Cookie cookie = Arrays.stream(req.getCookies())
                .filter(c -> c.getName().equals(Constant.REFRESH_TOKEN_COOKIE_NAME))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Constant.ERROR_REFRESH_TOKEN_NOT_FOUND_MSG));
        return cookie.getValue();
    }

    private void setCookie(HttpServletResponse res, String refreshToken) {
        Cookie cookie = new Cookie(Constant.REFRESH_TOKEN_COOKIE_NAME, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(REFRESH_TOKEN_EXPIRATION_IN_SECONDS);
        res.addCookie(cookie);
    }
}