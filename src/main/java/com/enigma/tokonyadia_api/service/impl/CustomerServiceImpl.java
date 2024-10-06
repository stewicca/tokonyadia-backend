package com.enigma.tokonyadia_api.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.enigma.tokonyadia_api.util.SortUtil;
import org.springframework.data.domain.Pageable;
import com.enigma.tokonyadia_api.entity.Customer;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.constant.Constant;
import org.springframework.data.domain.PageRequest;
import com.enigma.tokonyadia_api.dto.req.CustomerReq;
import com.enigma.tokonyadia_api.service.CustomerService;
import org.springframework.web.server.ResponseStatusException;
import com.enigma.tokonyadia_api.repository.CustomerRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Page<Customer> getAll(PageReq req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), SortUtil.parseSort(req.getSort()));
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer getById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_GET_CUSTOMER_MSG));
    }

    @Override
    public Customer create(CustomerReq req) {
        Customer customer = Customer.builder().name(req.getName()).address(req.getAddress()).phone(req.getPhone()).email(req.getEmail()).build();
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(String id, CustomerReq req) {
        Customer customer = getById(id);
        customer.setName(req.getName());
        customer.setAddress(req.getAddress());
        return customerRepository.save(customer);
    }

    @Override
    public void delete(String id) {
        Customer customer = getById(id);
        customerRepository.delete(customer);
    }
}