package com.enigma.tokonyadia_api.controller;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.*;
import com.enigma.tokonyadia_api.service.UserService;
import com.enigma.tokonyadia_api.util.ResUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "User")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = Constant.USER_API_URL)
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllUser(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "false") boolean isDeleted
    ) {
        PageReq req = PageReq.builder()
                .page(page)
                .size(size)
                .sort(sort)
                .isDeleted(isDeleted)
                .build();
        return ResUtil.buildPageRes(
                HttpStatus.OK,
                Constant.SUCCESS_GET_ALL_USER_MSG,
                userService.getAll(req)
        );
    }

    @PreAuthorize("hasRole('ADMIN') or #id.equals(authentication.principal.id)")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_GET_USER_MSG,
                userService.getById(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserReq req) {
        return ResUtil.buildRes(
                HttpStatus.CREATED,
                Constant.SUCCESS_CREATE_USER_MSG,
                userService.create(req)
        );
    }

    @PreAuthorize("#id.equals(authentication.principal.id)")
    @PutMapping(path = "/{id}/password/change")
    public ResponseEntity<?> changePassword(@PathVariable("id") String id, @RequestBody UserChangePasswordReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_CHANGE_PASSWORD_MSG,
                userService.changePassword(id, req)
        );
    }

    @PreAuthorize("hasRole('ADMIN') or #id.equals(authentication.principal.id)")
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody UserUpdateReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_UPDATE_USER_MSG,
                userService.update(id, req)
        );
    }

    @PreAuthorize("hasRole('ADMIN') or #id.equals(authentication.principal.id)")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        userService.delete(id);
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_DELETE_USER_MSG,
                null
        );
    }
}