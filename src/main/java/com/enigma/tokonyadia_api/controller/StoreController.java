package com.enigma.tokonyadia_api.controller;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.req.StoreReq;
import com.enigma.tokonyadia_api.service.StoreService;
import com.enigma.tokonyadia_api.util.ResUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Store")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = Constant.STORE_API_URL)
public class StoreController {
    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<?> getAllStore(
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
                Constant.SUCCESS_GET_ALL_STORE_MSG,
                storeService.getAll(req)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStoreById(@PathVariable("id") String id) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_GET_STORE_MSG,
                storeService.getById(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createStore(@RequestBody StoreReq req) {
        return ResUtil.buildRes(
                HttpStatus.CREATED,
                Constant.SUCCESS_CREATE_STORE_MSG,
                storeService.create(req)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateStore(@PathVariable("id") String id, @RequestBody StoreReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_UPDATE_STORE_MSG,
                storeService.update(id, req)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable("id") String id) {
        storeService.delete(id);
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_DELETE_STORE_MSG,
                null
        );
    }
}