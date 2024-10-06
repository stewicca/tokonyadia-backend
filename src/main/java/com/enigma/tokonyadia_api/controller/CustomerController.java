package com.enigma.tokonyadia_api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import com.enigma.tokonyadia_api.util.ResUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.CustomerReq;
import com.enigma.tokonyadia_api.service.CustomerService;

@AllArgsConstructor
@RestController
@RequestMapping(path = Constant.CUSTOMER_API_URL)
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getAllCustomer(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "5") Integer size, @RequestParam(required = false) String sort) {
        PageReq req = PageReq.builder().page(page).size(size).sort(sort).build();
        return ResUtil.buildPageRes(HttpStatus.OK, Constant.SUCCESS_GET_ALL_CUSTOMER_MSG, customerService.getAll(req));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        return ResUtil.buildRes(HttpStatus.OK, Constant.SUCCESS_GET_CUSTOMER_MSG, customerService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerReq req) {
        return ResUtil.buildRes(HttpStatus.CREATED, Constant.SUCCESS_CREATE_CUSTOMER_MSG, customerService.create(req));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id, @RequestBody CustomerReq req) {
        return ResUtil.buildRes(HttpStatus.OK, Constant.SUCCESS_UPDATE_CUSTOMER_MSG, customerService.update(id, req));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        customerService.delete(id);
        return ResUtil.buildRes(HttpStatus.OK, Constant.SUCCESS_DELETE_CUSTOMER_MSG, null);
    }
}