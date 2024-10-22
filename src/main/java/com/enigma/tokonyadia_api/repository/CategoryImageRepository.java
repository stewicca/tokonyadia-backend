package com.enigma.tokonyadia_api.repository;

import com.enigma.tokonyadia_api.entity.CategoryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryImageRepository extends JpaRepository<CategoryImage, String> {
}