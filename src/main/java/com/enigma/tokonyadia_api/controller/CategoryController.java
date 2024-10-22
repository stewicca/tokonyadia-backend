package com.enigma.tokonyadia_api.controller;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.CategoryReq;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.service.CategoryService;
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
@Tag(name = "Category")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = Constant.CATEGORY_API_URL)
public class CategoryController {
    private final ObjectMapper objectMapper;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategory(
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
                Constant.SUCCESS_GET_ALL_CATEGORY_MSG,
                categoryService.getAll(req)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") String id) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_GET_CATEGORY_MSG,
                categoryService.getById(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCategory(
            @Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart(name = "images", required = false) List<MultipartFile> multipartFiles,
            @RequestPart(name = "category") String category
    ) {
        try {
            CategoryReq req = objectMapper.readValue(category, CategoryReq.class);
            return ResUtil.buildRes(
                    HttpStatus.CREATED,
                    Constant.SUCCESS_CREATE_CATEGORY_MSG,
                    categoryService.create(multipartFiles, req)
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
    public ResponseEntity<?> updateCategory(
            @PathVariable("id") String id,
            @Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart(name = "images", required = false) List<MultipartFile> multipartFiles,
            @RequestPart(name = "category") String category
    ) {
        try {
            CategoryReq req = objectMapper.readValue(category, CategoryReq.class);
            return ResUtil.buildRes(
                    HttpStatus.OK,
                    Constant.SUCCESS_UPDATE_CATEGORY_MSG,
                    categoryService.update(id, multipartFiles, req)
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
    @PutMapping(path = "images/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCategoryImage(
            @PathVariable("id") String id,
            @Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart(name = "image") MultipartFile file
    ) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_UPDATE_CATEGORY_IMAGE_MSG,
                categoryService.updateImage(file, id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/images/{id}")
    public ResponseEntity<?> deleteCategoryImage(@PathVariable("id") String id) {
        categoryService.deleteImage(id);
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_DELETE_CATEGORY_IMAGE_MSG,
                null
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") String id) {
        categoryService.delete(id);
        return ResUtil.buildRes(
                HttpStatus.OK,
                Constant.SUCCESS_DELETE_CATEGORY_MSG,
                null
        );
    }
}