package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.entity.Customer;

import java.util.List;

public interface CustomerService {
    public List<Customer> getAll();
    public Customer getById(String id);
    public Customer create(Customer customer);
    public Customer update(String id, Customer customer);
    public String delete(String id);
}