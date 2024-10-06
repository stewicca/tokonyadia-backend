package com.enigma.tokonyadia_api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import com.enigma.tokonyadia_api.util.ResUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.ProductReq;
import com.enigma.tokonyadia_api.service.ProductService;

@AllArgsConstructor
@RestController
@RequestMapping(path = Constant.PRODUCT_API_URL)
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProduct(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "5") Integer size, @RequestParam(required = false) String sort) {
        return ResUtil.buildRes(HttpStatus.OK, Constant.SUCCESS_GET_ALL_PRODUCT_MSG, productService.getAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        return ResUtil.buildRes(HttpStatus.OK, Constant.SUCCESS_GET_PRODUCT_MSG, productService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductReq req) {
        return ResUtil.buildRes(HttpStatus.CREATED, Constant.SUCCESS_CREATE_PRODUCT_MSG, productService.create(req));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody ProductReq req) {
        return ResUtil.buildRes(HttpStatus.OK, Constant.SUCCESS_UPDATE_PRODUCT_MSG, productService.update(id, req));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.delete(id);
        return ResUtil.buildRes(HttpStatus.OK, Constant.SUCCESS_DELETE_PRODUCT_MSG, null);
    }
}