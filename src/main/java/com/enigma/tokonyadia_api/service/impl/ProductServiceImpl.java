package com.enigma.tokonyadia_api.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.enigma.tokonyadia_api.entity.Product;
import com.enigma.tokonyadia_api.service.ProductService;
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
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(String id, Product product) {
        Product isProduct = getById(id);

        if (isProduct == null) return null;

        isProduct.setName(product.getName());
        isProduct.setDesc(product.getDesc());
        isProduct.setPrice(product.getPrice());

        return productRepository.save(isProduct);
    }

    @Override
    public String delete(String id) {
        Product product = getById(id);

        if (product == null) return "Product not found";

        productRepository.delete(product);

        return "Product deleted";
    }
}