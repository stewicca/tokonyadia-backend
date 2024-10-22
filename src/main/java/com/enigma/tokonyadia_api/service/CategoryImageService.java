package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.dto.res.FileDownloadRes;
import com.enigma.tokonyadia_api.entity.Category;
import com.enigma.tokonyadia_api.entity.CategoryImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryImageService {
    CategoryImage create(MultipartFile multipartFile, Category category);

    List<CategoryImage> createBulk(List<MultipartFile> multipartFiles, Category category);

    CategoryImage update(String id, MultipartFile multipartFile);

    void delete(String id);

    FileDownloadRes download(String id);
}