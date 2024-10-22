package com.enigma.tokonyadia_api.controller;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.*;
import com.enigma.tokonyadia_api.service.CartService;
import com.enigma.tokonyadia_api.service.CustomerAddressService;
import com.enigma.tokonyadia_api.service.CustomerService;
import com.enigma.tokonyadia_api.util.ResUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Customer")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = Constant.CUSTOMER_API_URL)
public class CustomerController {
    private final CartService cartService;
    private final CustomerService customerService;
    private final CustomerAddressService customerAddressService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllCustomer(
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
                Constant.SUCCESS_GET_ALL_CUSTOMER_MSG,
                customerService.getAll(req)
        );
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('CUSTOMER') and @permissionEvaluationServiceImpl.hasAccessToCustomer(#id, authentication.principal.id))")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") String id) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_GET_CUSTOMER_MSG,
                customerService.getById(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerReq req) {
        return ResUtil.buildRes(
                HttpStatus.CREATED,
                Constant.SUCCESS_CREATE_CUSTOMER_MSG,
                customerService.create(req)
        );
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('CUSTOMER') and @permissionEvaluationServiceImpl.hasAccessToCustomer(#id, authentication.principal.id))")
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") String id, @RequestBody CustomerUpdateReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_UPDATE_CUSTOMER_MSG,
                customerService.update(id, req)
        );
    }

    @PreAuthorize("hasRole('CUSTOMER') and @permissionEvaluationServiceImpl.hasAccessToCustomer(#id, authentication.principal.id)")
    @PostMapping(path = "/{id}/address")
    public ResponseEntity<?> addAddress(@PathVariable("id") String id, @RequestBody CustomerAddressReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_CREATE_CUSTOMER_ADDRESS_MSG,
                customerAddressService.create(id, req)
        );
    }

    @PreAuthorize("hasRole('CUSTOMER') and @permissionEvaluationServiceImpl.hasAccessToCustomer(#id, authentication.principal.id)")
    @PutMapping(path = "/{id}/address/{customerAddressId}")
    public ResponseEntity<?> updateAddress(@PathVariable("id") String id, @PathVariable String customerAddressId, @RequestBody CustomerAddressReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_UPDATE_CUSTOMER_ADDRESS_MSG,
                customerAddressService.update(id, customerAddressId, req)
        );
    }

    @PreAuthorize("hasRole('CUSTOMER') and @permissionEvaluationServiceImpl.hasAccessToCustomer(#id, authentication.principal.id)")
    @PostMapping(path = "/{id}/cart/checkout")
    public ResponseEntity<?> checkout(@PathVariable("id") String id, @RequestBody CartCheckoutReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_CHECKOUT_MSG,
                cartService.checkout(id, req)
        );
    }

    @PreAuthorize("hasRole('CUSTOMER') and @permissionEvaluationServiceImpl.hasAccessToCustomer(#id, authentication.principal.id)")
    @PostMapping(path = "/{id}/cart")
    public ResponseEntity<?> addProduct(@PathVariable("id") String id, @RequestBody CartReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_ADD_PRODUCT_MSG,
                cartService.add(id, req)
        );
    }

    @PreAuthorize("hasRole('CUSTOMER') and @permissionEvaluationServiceImpl.hasAccessToCustomer(#id, authentication.principal.id)")
    @PostMapping(path = "/{id}/cart/increase")
    public ResponseEntity<?> increaseProduct(@PathVariable("id") String id, @RequestBody CartUpdateReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_INCREASE_PRODUCT_QUANTITY_MSG,
                cartService.increase(id, req)
        );
    }

    @PreAuthorize("hasRole('CUSTOMER') and @permissionEvaluationServiceImpl.hasAccessToCustomer(#id, authentication.principal.id)")
    @PostMapping(path = "/{id}/cart/decrease")
    public ResponseEntity<?> decreaseProduct(@PathVariable("id") String id, @RequestBody CartUpdateReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_DECREASE_PRODUCT_QUANTITY_MSG,
                cartService.decrease(id, req)
        );
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('CUSTOMER') and @permissionEvaluationServiceImpl.hasAccessToCustomer(#id, authentication.principal.id))")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") String id) {
        customerService.delete(id);
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_DELETE_CUSTOMER_MSG,
                null
        );
    }

    @PreAuthorize("hasRole('CUSTOMER') and @permissionEvaluationServiceImpl.hasAccessToCustomer(#id, authentication.principal.id)")
    @DeleteMapping(path = "/{id}/address/{customerAddressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") String id, @PathVariable String customerAddressId) {
        customerAddressService.delete(id, customerAddressId);
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_DELETE_CUSTOMER_ADDRESS_MSG,
                null
        );
    }

    @PreAuthorize("hasRole('CUSTOMER') and @permissionEvaluationServiceImpl.hasAccessToCustomer(#id, authentication.principal.id)")
    @DeleteMapping(path = "/{id}/cart/remove")
    public ResponseEntity<?> removeProduct(@PathVariable("id") String id, @RequestBody CartUpdateReq req) {
        cartService.remove(id, req);
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_REMOVE_PRODUCT_MSG,
                null
        );
    }
}