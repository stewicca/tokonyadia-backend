package com.enigma.tokonyadia_api.controller;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.OrderCreateReq;
import com.enigma.tokonyadia_api.dto.req.OrderSearchReq;
import com.enigma.tokonyadia_api.dto.req.OrderUpdateReq;
import com.enigma.tokonyadia_api.service.OrderService;
import com.enigma.tokonyadia_api.util.ResUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Order")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = Constant.ORDER_API_URL)
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER') or (hasRole('CUSTOMER') and @permissionEvaluationServiceImpl.hasAccessToCustomer(#customerId, authentication.principal.id))")
    @GetMapping
    public ResponseEntity<?> getAllOrder(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "false") boolean isDeleted,
            @RequestParam(required = false) String storeId,
            @RequestParam(required = false) String productId,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String voucherId,
            @RequestParam(required = false) String orderStatus,
            @RequestParam(required = false) String query
    ) {
        OrderSearchReq orderSearchReq = OrderSearchReq.builder()
                .page(page)
                .size(size)
                .sort(sort)
                .isDeleted(isDeleted)
                .storeId(storeId)
                .productId(productId)
                .customerId(customerId)
                .voucherId(voucherId)
                .orderStatus(orderStatus)
                .query(query)
                .build();
        return ResUtil.buildPageRes(
                HttpStatus.OK,
                Constant.SUCCESS_GET_ALL_ORDER_MSG,
                orderService.getAll(orderSearchReq)
        );
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") String id) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_GET_ORDER_MSG,
                orderService.getById(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER')")
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateReq orderCreateReq) {
        return ResUtil.buildRes(
                HttpStatus.CREATED,
                Constant.SUCCESS_CREATE_ORDER_MSG,
                orderService.create(orderCreateReq)
        );
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") String id, @RequestBody OrderUpdateReq orderUpdateReq) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_UPDATE_ORDER_MSG,
                orderService.update(id, orderUpdateReq)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") String id) {
        orderService.delete(id);
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_DELETE_ORDER_MSG,
                null
        );
    }
}