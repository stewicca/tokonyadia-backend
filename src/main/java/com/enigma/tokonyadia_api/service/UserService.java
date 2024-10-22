package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.dto.req.*;
import com.enigma.tokonyadia_api.dto.res.UserRes;
import com.enigma.tokonyadia_api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Page<UserRes> getAll(PageReq req);

    UserRes getById(String id);

    User getOne(String id);

    UserRes create(UserReq req);

    UserRes changePassword(String id, UserChangePasswordReq req);

    UserRes update(String id, UserUpdateReq req);

    void delete(String id);
}