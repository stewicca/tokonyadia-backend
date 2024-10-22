package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.CategoryReq;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.res.CategoryRes;
import com.enigma.tokonyadia_api.entity.Category;
import com.enigma.tokonyadia_api.entity.CategoryImage;
import com.enigma.tokonyadia_api.repository.CategoryRepository;
import com.enigma.tokonyadia_api.service.CategoryImageService;
import com.enigma.tokonyadia_api.service.CategoryService;
import com.enigma.tokonyadia_api.util.LogUtil;
import com.enigma.tokonyadia_api.util.MapperUtil;
import com.enigma.tokonyadia_api.util.SortUtil;
import com.enigma.tokonyadia_api.util.ValidationUtil;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final EntityManager entityManager;
    private final ValidationUtil validationUtil;
    private final CategoryRepository categoryRepository;
    private final CategoryImageService categoryImageService;

    @Override
    public Page<CategoryRes> getAll(PageReq req) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedFilter");
        filter.setParameter("isDeleted", req.isDeleted());
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getSize(),
                SortUtil.parseSort(req.getSort())
        );
        Page<Category> categories = categoryRepository.findAll(pageable);
        session.disableFilter("deletedFilter");
        return categories.map(MapperUtil::toCategoryRes);
    }

    @Override
    public CategoryRes getById(String id) {
        return MapperUtil.toCategoryRes(getOne(id));
    }

    @Override
    public Category getOne(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_GET_CATEGORY_MSG));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryRes create(List<MultipartFile> multipartFiles, CategoryReq req) {
        LogUtil.info("creating category");
        validationUtil.validate(req);
        Category category = Category.builder()
                .name(req.getName())
                .build();
        categoryRepository.saveAndFlush(category);
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            List<CategoryImage> categoryImages = categoryImageService.createBulk(multipartFiles, category);
            category.setImages(categoryImages);
        }
        LogUtil.info("finished creating category");
        return MapperUtil.toCategoryRes(category);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryRes update(String id, List<MultipartFile> multipartFiles, CategoryReq req) {
        LogUtil.info("updating category");
        validationUtil.validate(req);
        Category category = getOne(id);
        category.setName(req.getName());
        categoryRepository.saveAndFlush(category);
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            List<CategoryImage> categoryImages = categoryImageService.createBulk(multipartFiles, category);
            if (category.getImages() != null) {
                category.getImages().addAll(categoryImages);
            } else {
                category.setImages(categoryImages);
            }
        }
        LogUtil.info("finished creating category");
        return MapperUtil.toCategoryRes(category);
    }

    @Override
    public CategoryRes updateImage(MultipartFile file, String imageId) {
        CategoryImage categoryImage = categoryImageService.update(imageId, file);
        return MapperUtil.toCategoryRes(categoryImage.getCategory());
    }

    @Override
    public void deleteImage(String imageId) {
        categoryImageService.delete(imageId);
    }

    @Override
    public void delete(String id) {
        LogUtil.info("deleting category");
        Category category = getOne(id);
        category.setDeleted(!category.isDeleted());
        categoryRepository.save(category);
        LogUtil.info("finished creating category");
    }
}