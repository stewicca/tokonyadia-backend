package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.req.ProductReq;
import com.enigma.tokonyadia_api.dto.res.ProductRes;
import com.enigma.tokonyadia_api.entity.Category;
import com.enigma.tokonyadia_api.entity.Product;
import com.enigma.tokonyadia_api.entity.ProductImage;
import com.enigma.tokonyadia_api.repository.ProductRepository;
import com.enigma.tokonyadia_api.service.CategoryService;
import com.enigma.tokonyadia_api.service.ProductImageService;
import com.enigma.tokonyadia_api.service.ProductService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final EntityManager entityManager;
    private final ValidationUtil validationUtil;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final ProductImageService productImageService;

    @Override
    public Page<ProductRes> getAll(PageReq req) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedFilter");
        filter.setParameter("isDeleted", req.isDeleted());
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getSize(),
                SortUtil.parseSort(req.getSort())
        );
        Page<Product> products = productRepository.findAll(pageable);
        session.disableFilter("deletedFilter");
        return products.map(MapperUtil::toProductRes);
    }

    @Override
    public ProductRes getById(String id) {
        return MapperUtil.toProductRes(getOne(id));
    }

    @Override
    public Product getOne(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_GET_PRODUCT_MSG));
    }

    @Override
    public ProductRes create(List<MultipartFile> multipartFiles, ProductReq req) {
        LogUtil.info("creating product");
        validationUtil.validate(req);
        Category category = categoryService.getOne(req.getCategoryId());
        Product product = Product.builder()
                .name(req.getName())
                .description(req.getDescription())
                .category(category)
                .build();
        productRepository.saveAndFlush(product);
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            List<ProductImage> productImages = productImageService.createBulk(multipartFiles, product);
            product.setImages(productImages);
        }
        LogUtil.info("finished creating product");
        return MapperUtil.toProductRes(product);
    }

    @Override
    public ProductRes update(String id, List<MultipartFile> multipartFiles, ProductReq req) {
        LogUtil.info("updating product");
        validationUtil.validate(req);
        Category category = categoryService.getOne(req.getCategoryId());
        Product product = getOne(id);
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setCategory(category);
        productRepository.saveAndFlush(product);
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            List<ProductImage> productImages = productImageService.createBulk(multipartFiles, product);
            if (product.getImages() != null) {
                product.getImages().addAll(productImages);
            } else {
                product.setImages(productImages);
            }
        }
        LogUtil.info("finished updating product");
        return MapperUtil.toProductRes(product);
    }

    @Override
    public ProductRes updateImage(MultipartFile file, String imageId) {
        ProductImage productImage = productImageService.update(imageId, file);
        return MapperUtil.toProductRes(productImage.getProduct());
    }

    @Override
    public void deleteImage(String imageId) {
        productImageService.delete(imageId);
    }

    @Override
    public void delete(String id) {
        LogUtil.info("deleting product");
        Product product = getOne(id);
        product.setDeleted(!product.isDeleted());
        productRepository.save(product);
        LogUtil.info("deleting product");
    }
}