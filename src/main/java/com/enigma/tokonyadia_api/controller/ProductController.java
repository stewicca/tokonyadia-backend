package com.enigma.tokonyadia_api.controller;

import lombok.AllArgsConstructor;
import com.enigma.tokonyadia_api.entity.Product;
import org.springframework.web.bind.annotation.*;
import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.service.ProductService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = Constant.PRODUCT_API_URL)
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> getAllProduct() {
        return productService.getAll();
    }

    @GetMapping(path = "/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping(path = "/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
        return productService.update(id, product);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteProduct(@PathVariable String id) {
        return productService.delete(id);
    }
}