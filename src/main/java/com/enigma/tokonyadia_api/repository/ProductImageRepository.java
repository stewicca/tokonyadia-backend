package com.enigma.tokonyadia_api.repository;

import com.enigma.tokonyadia_api.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, String> {
}