package com.enigma.tokonyadia_api.controller;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.req.ProductReq;
import com.enigma.tokonyadia_api.service.ProductService;
import com.enigma.tokonyadia_api.util.ResUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Product")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = Constant.PRODUCT_API_URL)
public class ProductController {
    private final ObjectMapper objectMapper;
    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllProduct(
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
                Constant.SUCCESS_GET_ALL_PRODUCT_MSG,
                productService.getAll(req)
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") String id) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_GET_PRODUCT_MSG,
                productService.getById(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart(name = "images", required = false) List<MultipartFile> multipartFiles,
            @RequestPart(name = "product") String product
    ) {
        try {
            ProductReq req = objectMapper.readValue(product, ProductReq.class);
            return ResUtil.buildRes(
                    HttpStatus.CREATED,
                    Constant.SUCCESS_CREATE_PRODUCT_MSG,
                    productService.create(multipartFiles, req)
            );
        } catch (Exception e) {
            return ResUtil.buildRes(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    null
            );
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProduct(
            @PathVariable("id") String id,
            @Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart(name = "images", required = false) List<MultipartFile> multipartFiles,
            @RequestPart(name = "product") String product
    ) {
        try {
            ProductReq req = objectMapper.readValue(product, ProductReq.class);
            return ResUtil.buildRes(
                    HttpStatus.OK,
                    Constant.SUCCESS_UPDATE_PRODUCT_MSG,
                    productService.update(id, multipartFiles, req)
            );
        } catch (Exception e) {
            return ResUtil.buildRes(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    null
            );
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/images/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProductImage(
            @PathVariable("id") String id,
            @Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart(name = "image") MultipartFile file
    ) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_UPDATE_PRODUCT_IMAGE_MSG,
                productService.updateImage(file, id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/images/{id}")
    public ResponseEntity<?> deleteProductImage(@PathVariable("id") String id) {
        productService.deleteImage(id);
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_DELETE_PRODUCT_IMAGE_MSG,
                null
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
        productService.delete(id);
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_DELETE_PRODUCT_MSG,
                null
        );
    }
}