package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.entity.Product;
import com.enigma.tokonyadia_api.dto.req.ProductReq;

import java.util.List;

public interface ProductService {
    public List<Product> getAll();
    public Product getById(String id);
    public Product create(ProductReq req);
    public Product update(String id, ProductReq req);
    public void delete(String id);
}