package com.enigma.tokonyadia_api.repository;

import com.enigma.tokonyadia_api.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, String> {
    List<CustomerAddress> findAllByCustomerId(String customerId);
}