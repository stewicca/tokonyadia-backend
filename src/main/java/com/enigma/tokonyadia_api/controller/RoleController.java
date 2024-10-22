package com.enigma.tokonyadia_api.controller;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.req.RoleReq;
import com.enigma.tokonyadia_api.service.RoleService;
import com.enigma.tokonyadia_api.util.ResUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Role")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping(path = Constant.ROLE_API_URL)
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getAllRole(
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
                Constant.SUCCESS_GET_All_ROLE_MSG,
                roleService.getAll(req)
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable("id") String id) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_GET_ROLE_MSG,
                roleService.getById(id)
        );
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleReq req) {
        return ResUtil.buildRes(
                HttpStatus.CREATED,
                Constant.SUCCESS_CREATE_ROLE_MSG,
                roleService.create(req)
        );
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateRole(@PathVariable("id") String id, @RequestBody RoleReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_UPDATE_ROLE_MSG,
                roleService.update(id, req)
        );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable("id") String id) {
        roleService.delete(id);
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_DELETE_ROLE_MSG,
                null
        );
    }
}