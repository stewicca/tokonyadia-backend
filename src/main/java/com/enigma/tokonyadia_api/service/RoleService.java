package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.req.RoleReq;
import com.enigma.tokonyadia_api.dto.res.RoleRes;
import com.enigma.tokonyadia_api.entity.Role;
import org.springframework.data.domain.Page;

public interface RoleService {
    Page<RoleRes> getAll(PageReq req);

    RoleRes getById(String id);

    Role getOne(String id);

    Role getByName(String name);

    RoleRes create(RoleReq req);

    RoleRes update(String id, RoleReq req);

    void delete(String id);
}