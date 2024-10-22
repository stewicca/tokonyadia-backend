package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.constant.FileType;
import com.enigma.tokonyadia_api.dto.res.FileDownloadRes;
import com.enigma.tokonyadia_api.dto.res.FileInfo;
import com.enigma.tokonyadia_api.entity.Category;
import com.enigma.tokonyadia_api.entity.CategoryImage;
import com.enigma.tokonyadia_api.repository.CategoryImageRepository;
import com.enigma.tokonyadia_api.service.CategoryImageService;
import com.enigma.tokonyadia_api.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryImageServiceImpl implements CategoryImageService {
    private final FileStorageService fileStorageService;
    private final CategoryImageRepository categoryImageRepository;
    private final List<String> contentTypes = List.of("image/jpg", "image/jpeg", "image/png", "image/webp");
    private final String CATEGORY = "category";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryImage create(MultipartFile multipartFile, Category category) {
        FileInfo fileInfo = fileStorageService.storeFile(FileType.IMAGE, CATEGORY, multipartFile, contentTypes);
        CategoryImage categoryImage = CategoryImage.builder()
                .filename(fileInfo.getFilename())
                .contentType(multipartFile.getContentType())
                .size(multipartFile.getSize())
                .path(fileInfo.getPath())
                .category(category)
                .build();
        return categoryImageRepository.save(categoryImage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<CategoryImage> createBulk(List<MultipartFile> multipartFiles, Category category) {
        return multipartFiles.stream().map(multipartFile -> create(multipartFile, category)).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryImage update(String id, MultipartFile multipartFile) {
        CategoryImage categoryImage = getOne(id);
        FileInfo fileInfo = fileStorageService.storeFile(FileType.IMAGE, CATEGORY, multipartFile, contentTypes);
        fileStorageService.deleteFile(categoryImage.getPath());
        categoryImage.setPath(fileInfo.getPath());
        categoryImageRepository.saveAndFlush(categoryImage);
        return categoryImage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        CategoryImage categoryImage = getOne(id);
        categoryImageRepository.delete(categoryImage);
        fileStorageService.deleteFile(categoryImage.getPath());
    }

    @Override
    public FileDownloadRes download(String id) {
        CategoryImage categoryImage = getOne(id);
        Resource resource = fileStorageService.readFile(categoryImage.getPath());
        return FileDownloadRes.builder()
                .resource(resource)
                .contentType(categoryImage.getContentType())
                .build();
    }

    public CategoryImage getOne(String id) {
        return categoryImageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_CATEGORY_IMAGE_NOT_FOUND_MSG));
    }
}