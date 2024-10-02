package com.enigma.tokonyadia_api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.enigma.tokonyadia_api.entity.Customer;
import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.service.CustomerService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = Constant.CUSTOMER_API_URL)
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomer() {
        return customerService.getAll();
    }

    @GetMapping(path = "/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerService.getById(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @PutMapping(path = "/{id}")
    public Customer updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        return customerService.update(id, customer);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteCustomer(@PathVariable String id) {
        return customerService.delete(id);
    }
}