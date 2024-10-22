package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.req.RoleReq;
import com.enigma.tokonyadia_api.dto.res.RoleRes;
import com.enigma.tokonyadia_api.entity.Role;
import com.enigma.tokonyadia_api.repository.RoleRepository;
import com.enigma.tokonyadia_api.service.RoleService;
import com.enigma.tokonyadia_api.util.LogUtil;
import com.enigma.tokonyadia_api.util.MapperUtil;
import com.enigma.tokonyadia_api.util.SortUtil;
import com.enigma.tokonyadia_api.util.ValidationUtil;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final EntityManager entityManager;
    private final RoleRepository roleRepository;
    private final ValidationUtil validationUtil;

    @PostConstruct
    public void init() {
        if (roleRepository.existsByName("Admin")) return;
        Role role = Role.builder()
                .name("Admin")
                .build();
        roleRepository.save(role);
    }

    @Override
    public Page<RoleRes> getAll(PageReq req) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedFilter");
        filter.setParameter("isDeleted", req.isDeleted());
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getSize(),
                SortUtil.parseSort(req.getSort())
        );
        Page<Role> roles = roleRepository.findAll(pageable);
        session.disableFilter("deletedFilter");
        return roles.map(MapperUtil::toRoleRes);
    }

    @Override
    public RoleRes getById(String id) {
        return MapperUtil.toRoleRes(getOne(id));
    }

    @Override
    public Role getOne(String id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_GET_ROLE_MSG));
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_GET_ROLE_MSG));
    }

    @Override
    public RoleRes create(RoleReq req) {
        LogUtil.info("creating role");
        validationUtil.validate(req);
        Role role = Role.builder()
                .name(req.getName())
                .build();
        roleRepository.saveAndFlush(role);
        LogUtil.info("finished creating role");
        return MapperUtil.toRoleRes(role);
    }

    @Override
    public RoleRes update(String id, RoleReq req) {
        LogUtil.info("updating role");
        validationUtil.validate(req);
        Role role = getOne(id);
        role.setName(req.getName());
        roleRepository.saveAndFlush(role);
        LogUtil.info("finished updating role");
        return MapperUtil.toRoleRes(role);
    }

    @Override
    public void delete(String id) {
        LogUtil.info("deleting role");
        Role role = getOne(id);
        role.setDeleted(!role.isDeleted());
        roleRepository.save(role);
        LogUtil.info("finished deleting role");
    }
}