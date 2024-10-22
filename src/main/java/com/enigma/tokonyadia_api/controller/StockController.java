package com.enigma.tokonyadia_api.controller;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.StockReq;
import com.enigma.tokonyadia_api.dto.req.StockSearchReq;
import com.enigma.tokonyadia_api.service.StockService;
import com.enigma.tokonyadia_api.util.ResUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Stock")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = Constant.STOCK_API_URL)
public class StockController {
    private final StockService stockService;

    @GetMapping
    public ResponseEntity<?> getAllStock(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "false") boolean isDeleted,
            @RequestParam(required = false) String storeId,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) String query
    ) {
        StockSearchReq req = StockSearchReq.builder()
                .page(page)
                .size(size)
                .sort(sort)
                .isDeleted(isDeleted)
                .storeId(storeId)
                .categoryId(categoryId)
                .query(query)
                .build();
        return ResUtil.buildPageRes(
                HttpStatus.OK,
                Constant.SUCCESS_GET_ALL_STOCK_MSG,
                stockService.getAll(req)
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStock(@PathVariable("id") String id) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_GET_STOCK_MSG,
                stockService.getById(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createStock(@RequestBody StockReq req) {
        return ResUtil.buildRes(
                HttpStatus.CREATED,
                Constant.SUCCESS_CREATE_STOCK_MSG,
                stockService.create(req)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateStock(@PathVariable("id") String id, @RequestBody StockReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_UPDATE_STOCK_MSG,
                stockService.update(id, req)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable("id") String id) {
        stockService.delete(id);
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_DELETE_STOCK_MSG,
                null
        );
    }
}