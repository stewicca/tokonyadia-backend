package com.enigma.tokonyadia_api.repository;

import com.enigma.tokonyadia_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}