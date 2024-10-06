package com.enigma.tokonyadia_api.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.enigma.tokonyadia_api.entity.Product;
import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.ProductReq;
import com.enigma.tokonyadia_api.service.ProductService;
import org.springframework.web.server.ResponseStatusException;
import com.enigma.tokonyadia_api.repository.ProductRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_GET_PRODUCT_MSG));
    }

    @Override
    public Product create(ProductReq req) {
        Product product = Product.builder().name(req.getName()).description(req.getDescription()).build();
        return productRepository.save(product);
    }

    @Override
    public Product update(String id, ProductReq req) {
        Product product = getById(id);
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        return productRepository.save(product);
    }

    @Override
    public void delete(String id) {
        Product product = getById(id);
        productRepository.delete(product);
    }
}