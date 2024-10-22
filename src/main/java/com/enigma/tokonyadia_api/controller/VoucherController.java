package com.enigma.tokonyadia_api.controller;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.req.VoucherReq;
import com.enigma.tokonyadia_api.service.VoucherService;
import com.enigma.tokonyadia_api.util.ResUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Voucher")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping(path = Constant.VOUCHER_API_URL)
public class VoucherController {
    private final VoucherService voucherService;

    @GetMapping
    public ResponseEntity<?> getAllVouchers(
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
                Constant.SUCCESS_GET_ALL_VOUCHER_MSG,
                voucherService.getAll(req)
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable("id") String id) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_GET_VOUCHER_MSG,
                voucherService.getById(id)
        );
    }

    @PostMapping
    public ResponseEntity<?> createVoucher(@RequestBody VoucherReq req) {
        return ResUtil.buildRes(
                HttpStatus.CREATED,
                Constant.SUCCESS_CREATE_VOUCHER_MSG,
                voucherService.create(req)
        );
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateVoucher(@PathVariable("id") String id, @RequestBody VoucherReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_UPDATE_VOUCHER_MSG,
                voucherService.update(id, req)
        );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable("id") String id) {
        voucherService.delete(id);
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_DELETE_VOUCHER_MSG,
                null
        );
    }
}