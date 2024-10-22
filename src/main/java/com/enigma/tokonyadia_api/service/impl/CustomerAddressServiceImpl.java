package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.CustomerAddressReq;
import com.enigma.tokonyadia_api.dto.res.CustomerAddressRes;
import com.enigma.tokonyadia_api.entity.Customer;
import com.enigma.tokonyadia_api.entity.CustomerAddress;
import com.enigma.tokonyadia_api.repository.CustomerAddressRepository;
import com.enigma.tokonyadia_api.service.CustomerAddressService;
import com.enigma.tokonyadia_api.service.CustomerService;
import com.enigma.tokonyadia_api.util.LogUtil;
import com.enigma.tokonyadia_api.util.MapperUtil;
import com.enigma.tokonyadia_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {
    private final CustomerService customerService;
    private final ValidationUtil validationUtil;
    private final CustomerAddressRepository customerAddressRepository;

    @Override
    public CustomerAddressRes getById(String customerId, String id) {
        CustomerAddress customerAddress = getOne(id);
        if (!customerAddress.getCustomer().getId().equals(customerId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constant.ERROR_UNAUTHORIZED_MSG);
        }
        return MapperUtil.toCustomerAddressRes(customerAddress);
    }

    @Override
    public CustomerAddress getOne(String id) {
        return customerAddressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_CUSTOMER_ADDRESS_NOT_FOUND_MSG));
    }

    @Override
    public CustomerAddressRes create(String customerId, CustomerAddressReq req) {
        LogUtil.info("creating address");
        validationUtil.validate(req);
        Customer customer = customerService.getOne(customerId);
        CustomerAddress customerAddress = CustomerAddress.builder()
                .customer(customer)
                .address(req.getAddress())
                .build();
        customerAddressRepository.saveAndFlush(customerAddress);
        LogUtil.info("finished creating address");
        return MapperUtil.toCustomerAddressRes(customerAddress);
    }

    @Override
    public CustomerAddressRes update(String customerId, String id, CustomerAddressReq req) {
        LogUtil.info("updating address");
        validationUtil.validate(req);
        CustomerAddress customerAddress = getOne(id);
        if (!customerAddress.getCustomer().getId().equals(customerId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constant.ERROR_UNAUTHORIZED_MSG);
        }
        customerAddress.setAddress(req.getAddress());
        customerAddressRepository.saveAndFlush(customerAddress);
        LogUtil.info("finished updating address");
        return MapperUtil.toCustomerAddressRes(customerAddress);
    }

    @Override
    public void delete(String customerId, String id) {
        LogUtil.info("deleting address");
        CustomerAddress customerAddress = getOne(id);
        if (!customerAddress.getCustomer().getId().equals(customerId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constant.ERROR_UNAUTHORIZED_MSG);
        }
        customerAddressRepository.delete(customerAddress);
        LogUtil.info("finished deleting address");
    }
}