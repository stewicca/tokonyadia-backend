package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.dto.req.CategoryReq;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.res.CategoryRes;
import com.enigma.tokonyadia_api.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    Page<CategoryRes> getAll(PageReq req);

    CategoryRes getById(String id);

    Category getOne(String id);

    CategoryRes create(List<MultipartFile> multipartFiles, CategoryReq req);

    CategoryRes update(String id, List<MultipartFile> multipartFiles, CategoryReq req);

    CategoryRes updateImage(MultipartFile file, String imageId);

    void deleteImage(String imageId);

    void delete(String id);
}