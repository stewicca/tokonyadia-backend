package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.req.ProductReq;
import com.enigma.tokonyadia_api.dto.res.ProductRes;
import com.enigma.tokonyadia_api.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Page<ProductRes> getAll(PageReq req);

    ProductRes getById(String id);

    Product getOne(String id);

    ProductRes create(List<MultipartFile> multipartFiles, ProductReq req);

    ProductRes update(String id, List<MultipartFile> multipartFiles, ProductReq req);

    ProductRes updateImage(MultipartFile file, String imageId);

    void deleteImage(String imageId);

    void delete(String id);
}