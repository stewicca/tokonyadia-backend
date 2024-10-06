package com.enigma.tokonyadia_api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import com.enigma.tokonyadia_api.util.ResUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.enigma.tokonyadia_api.dto.req.StoreReq;
import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.service.StoreService;

@AllArgsConstructor
@RestController
@RequestMapping(path = Constant.STORE_API_URL)
public class StoreController {
    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<?> getAllStore() {
        return ResUtil.buildRes(HttpStatus.OK, Constant.SUCCESS_GET_ALL_STORE_MSG, storeService.getAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStoreById(@PathVariable String id) {
        return ResUtil.buildRes(HttpStatus.OK, Constant.SUCCESS_GET_STORE_MSG, storeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createStore(@RequestBody StoreReq req) {
        return ResUtil.buildRes(HttpStatus.CREATED, Constant.SUCCESS_CREATE_STORE_MSG, storeService.create(req));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateStore(@PathVariable String id, @RequestBody StoreReq req) {
        return ResUtil.buildRes(HttpStatus.OK, Constant.SUCCESS_UPDATE_STORE_MSG, storeService.update(id, req));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable String id) {
        storeService.delete(id);
        return ResUtil.buildRes(HttpStatus.OK, Constant.SUCCESS_DELETE_STORE_MSG, null);
    }
}