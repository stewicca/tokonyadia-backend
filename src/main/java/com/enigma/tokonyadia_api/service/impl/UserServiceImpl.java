package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.*;
import com.enigma.tokonyadia_api.dto.res.UserRes;
import com.enigma.tokonyadia_api.entity.Role;
import com.enigma.tokonyadia_api.entity.User;
import com.enigma.tokonyadia_api.repository.UserRepository;
import com.enigma.tokonyadia_api.service.RoleService;
import com.enigma.tokonyadia_api.service.UserService;
import com.enigma.tokonyadia_api.util.LogUtil;
import com.enigma.tokonyadia_api.util.MapperUtil;
import com.enigma.tokonyadia_api.util.SortUtil;
import com.enigma.tokonyadia_api.util.ValidationUtil;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final RoleService roleService;
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.tokonyadia.admin-username}")
    private String ADMIN_USERNAME;

    @Value("${app.tokonyadia.admin-password}")
    private String ADMIN_PASSWORD;

    @PostConstruct
    public void init() {
        if (userRepository.existsByUsername(ADMIN_USERNAME)) return;
        User user = User.builder()
                .username(ADMIN_USERNAME)
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .role(roleService.getByName("Admin"))
                .build();
        userRepository.save(user);
    }

    @Override
    public Page<UserRes> getAll(PageReq req) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedFilter");
        filter.setParameter("isDeleted", req.isDeleted());
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getSize(),
                SortUtil.parseSort(req.getSort()));
        Page<User> users = userRepository.findAll(pageable);
        session.disableFilter("deletedFilter");
        return users.map(MapperUtil::toUserRes);
    }

    @Override
    public UserRes getById(String id) {
        return MapperUtil.toUserRes(getOne(id));
    }

    @Override
    public User getOne(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_GET_USER_MSG));
    }

    @Override
    public UserRes create(UserReq req) {
        validationUtil.validate(req);
        Role role = roleService.getOne(req.getRoleId());
        User user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(role)
                .build();
        userRepository.saveAndFlush(user);
        return MapperUtil.toUserRes(user);
    }

    @Override
    public UserRes changePassword(String id, UserChangePasswordReq req) {
        LogUtil.info("changing password");
        validationUtil.validate(req);
        User user = getOne(id);
        if (!passwordEncoder.matches(req.getOldPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constant.ERROR_VERIFY_PASSWORD_MSG);
        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.saveAndFlush(user);
        LogUtil.info("finished changing password");
        return MapperUtil.toUserRes(user);
    }

    @Override
    public UserRes update(String id, UserUpdateReq req) {
        LogUtil.info("updating user");
        validationUtil.validate(req);
        Role role = roleService.getOne(req.getRoleId());
        User user = getOne(id);
        user.setUsername(req.getUsername());
        user.setRole(role);
        userRepository.saveAndFlush(user);
        LogUtil.info("finished updating user");
        return MapperUtil.toUserRes(user);
    }

    @Override
    public void delete(String id) {
        LogUtil.info("deleting user");
        User user = getOne(id);
        user.setDeleted(!user.isDeleted());
        userRepository.save(user);
        LogUtil.info("finished deleting user");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().isDeleted())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constant.ERROR_USER_HAS_BEEN_DELETED_MSG);
        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_GET_USER_MSG);
        return user.get();
    }
}