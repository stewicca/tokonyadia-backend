package com.enigma.tokonyadia_api.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.enigma.tokonyadia_api.entity.Customer;
import com.enigma.tokonyadia_api.service.CustomerService;
import com.enigma.tokonyadia_api.repository.CustomerRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(String id, Customer customer) {
        Customer isCustomer = getById(id);

        if (isCustomer == null) return null;

        isCustomer.setName(customer.getName());
        isCustomer.setAddress(customer.getAddress());

        return customerRepository.save(isCustomer);
    }

    @Override
    public String delete(String id) {
        Customer customer = getById(id);

        if (customer == null) return "Customer not found";

        customerRepository.delete(customer);

        return "Customer deleted";
    }
}