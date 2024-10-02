package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.entity.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAll();
    public Product getById(String id);
    public Product create(Product product);
    public Product update(String id, Product product);
    public String delete(String id);
}