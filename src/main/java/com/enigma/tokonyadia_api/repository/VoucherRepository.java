package com.enigma.tokonyadia_api.repository;

import com.enigma.tokonyadia_api.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {
    Optional<Voucher> findByCodeAndDeletedFalse(String code);
}