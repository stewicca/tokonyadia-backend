package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.constant.FileType;
import com.enigma.tokonyadia_api.dto.res.FileDownloadRes;
import com.enigma.tokonyadia_api.dto.res.FileInfo;
import com.enigma.tokonyadia_api.entity.Product;
import com.enigma.tokonyadia_api.entity.ProductImage;
import com.enigma.tokonyadia_api.repository.ProductImageRepository;
import com.enigma.tokonyadia_api.service.FileStorageService;
import com.enigma.tokonyadia_api.service.ProductImageService;
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
public class ProductImageServiceImpl implements ProductImageService {
    private final FileStorageService fileStorageService;
    private final ProductImageRepository productImageRepository;
    private final List<String> contentTypes = List.of("image/jpg", "image/jpeg", "image/png", "image/webp");
    private final String PRODUCT = "product";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductImage create(MultipartFile multipartFile, Product product) {
        FileInfo fileInfo = fileStorageService.storeFile(FileType.IMAGE, PRODUCT, multipartFile, contentTypes);
        ProductImage productImage = ProductImage.builder()
                .filename(fileInfo.getFilename())
                .contentType(multipartFile.getContentType())
                .size(multipartFile.getSize())
                .path(fileInfo.getPath())
                .product(product)
                .build();
        return productImageRepository.save(productImage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ProductImage> createBulk(List<MultipartFile> multipartFiles, Product product) {
        return multipartFiles.stream().map(multipartFile -> create(multipartFile, product)).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductImage update(String id, MultipartFile multipartFile) {
        ProductImage productImage = getOne(id);
        FileInfo fileInfo = fileStorageService.storeFile(FileType.IMAGE, PRODUCT, multipartFile, contentTypes);
        fileStorageService.deleteFile(fileInfo.getFilename());
        productImage.setPath(fileInfo.getPath());
        productImageRepository.saveAndFlush(productImage);
        return productImage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        ProductImage productImage = getOne(id);
        productImageRepository.delete(productImage);
        fileStorageService.deleteFile(productImage.getFilename());
    }

    @Override
    public FileDownloadRes download(String id) {
        ProductImage productImage = getOne(id);
        Resource resource = fileStorageService.readFile(productImage.getPath());
        return FileDownloadRes.builder()
                .resource(resource)
                .contentType(productImage.getContentType())
                .build();
    }

    public ProductImage getOne(String id) {
        return productImageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_PRODUCT_IMAGE_NOT_FOUND_MSG));
    }
}