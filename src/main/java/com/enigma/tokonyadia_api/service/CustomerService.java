package com.enigma.tokonyadia_api.service;

import org.springframework.data.domain.Page;
import com.enigma.tokonyadia_api.entity.Customer;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.req.CustomerReq;

import java.util.List;

public interface CustomerService {
    public Page<Customer> getAll(PageReq req);
    public Customer getById(String id);
    public Customer create(CustomerReq req);
    public Customer update(String id, CustomerReq req);
    public void delete(String id);
}