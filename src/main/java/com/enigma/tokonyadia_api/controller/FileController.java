package com.enigma.tokonyadia_api.controller;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.res.FileDownloadRes;
import com.enigma.tokonyadia_api.service.CategoryImageService;
import com.enigma.tokonyadia_api.service.ProductImageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "File")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = Constant.IMAGE_API_URL)
public class FileController {
    private final CategoryImageService categoryImageService;
    private final ProductImageService productImageService;

    @GetMapping(path = "/category/{id}")
    public ResponseEntity<?> downloadCategoryImage(@PathVariable("id") String id) {
        FileDownloadRes res = categoryImageService.download(id);
        String headerValue = String.format("inline; filename=%s", res.getResource().getFilename());
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .contentType(MediaType.valueOf(res.getContentType()))
                .body(res.getResource());
    }

    @GetMapping(path = "/product/{id}")
    public ResponseEntity<?> downloadProductImage(@PathVariable("id") String id) {
        FileDownloadRes res = productImageService.download(id);
        String headerValue = String.format("inline; filename=%s", res.getResource().getFilename());
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .contentType(MediaType.valueOf(res.getContentType()))
                .body(res.getResource());
    }
}