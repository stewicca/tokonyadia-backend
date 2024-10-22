package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.dto.req.AuthReq;
import com.enigma.tokonyadia_api.dto.req.CustomerReq;
import com.enigma.tokonyadia_api.dto.req.UserReq;
import com.enigma.tokonyadia_api.dto.res.AuthRes;
import com.enigma.tokonyadia_api.dto.res.CustomerRes;
import com.enigma.tokonyadia_api.dto.res.UserRes;
import com.enigma.tokonyadia_api.entity.Role;
import com.enigma.tokonyadia_api.entity.User;
import com.enigma.tokonyadia_api.service.*;
import com.enigma.tokonyadia_api.util.MapperUtil;
import com.enigma.tokonyadia_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final RoleService roleService;
    private final UserService userService;
    private final ValidationUtil validationUtil;
    private final CustomerService customerService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerRes register(AuthReq req) {
        validationUtil.validate(req);
        Role role = roleService.getByName("Customer");
        UserReq userReq = UserReq.builder()
                .username(req.getUsername())
                .password(req.getPassword())
                .roleId(role.getId())
                .build();
        UserRes userRes = userService.create(userReq);
        CustomerReq customerReq = CustomerReq.builder()
                .userId(userRes.getId())
                .build();
        return customerService.create(customerReq);
    }

    @Override
    public AuthRes login(AuthReq req) {
        validationUtil.validate(req);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                req.getUsername(),
                req.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.generateRefreshToken(user.getId());
        return MapperUtil.toAuthRes(user, token, refreshToken);
    }

    @Override
    public void logout(String bearerToken) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        refreshTokenService.deleteRefreshToken(user.getId());
        jwtService.blacklistAccessToken(bearerToken);
    }

    @Override
    public AuthRes refreshToken(String refreshToken) {
        String userId = refreshTokenService.getUserIdFromRefreshToken(refreshToken);
        User user = userService.getOne(userId);
        String newToken = jwtService.generateToken(user);
        String newRefreshToken = refreshTokenService.rotateRefreshToken(userId);
        return MapperUtil.toAuthRes(user, newToken, newRefreshToken);
    }
}