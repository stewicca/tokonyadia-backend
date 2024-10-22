package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.dto.req.CustomerAddressReq;
import com.enigma.tokonyadia_api.dto.res.CustomerAddressRes;
import com.enigma.tokonyadia_api.entity.CustomerAddress;

public interface CustomerAddressService {
    CustomerAddressRes getById(String customerId, String id);

    CustomerAddress getOne(String id);

    CustomerAddressRes create(String customerId, CustomerAddressReq req);

    CustomerAddressRes update(String customerId, String id, CustomerAddressReq req);

    void delete(String customerId, String id);
}