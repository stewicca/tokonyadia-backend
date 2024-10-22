package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.dto.req.CustomerReq;
import com.enigma.tokonyadia_api.dto.req.CustomerUpdateReq;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.res.CustomerRes;
import com.enigma.tokonyadia_api.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Page<CustomerRes> getAll(PageReq req);

    CustomerRes getById(String id);

    Customer getOne(String id);

    CustomerRes create(CustomerReq req);

    CustomerRes update(String id, CustomerUpdateReq req);

    void delete(String id);

    boolean existsByIdAndUserId(String id, String userId);
}