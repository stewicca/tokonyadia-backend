package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.service.CustomerService;
import com.enigma.tokonyadia_api.service.PermissionEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PermissionEvaluationServiceImpl implements PermissionEvaluationService {
    private final CustomerService customerService;

    @Override
    public boolean hasAccessToCustomer(String customerId, String userId) {
        return customerService.existsByIdAndUserId(customerId, userId);
    }
}