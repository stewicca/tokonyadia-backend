package com.enigma.tokonyadia_api.repository;

import com.enigma.tokonyadia_api.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, String> {
}