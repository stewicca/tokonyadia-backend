package com.enigma.tokonyadia_api.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import com.enigma.tokonyadia_api.entity.Store;
import org.springframework.stereotype.Service;
import com.enigma.tokonyadia_api.dto.req.StoreReq;
import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.service.StoreService;
import com.enigma.tokonyadia_api.repository.StoreRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    @Override
    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @Override
    public Store getById(String id) {
        return storeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_GET_STORE_MSG));
    }

    @Override
    public Store create(StoreReq req) {
        Store store = Store.builder().name(req.getName()).address(req.getAddress()).phone(req.getPhone()).build();
        return storeRepository.save(store);
    }

    @Override
    public Store update(String id, StoreReq req) {
        Store store = getById(id);
        store.setName(req.getName());
        store.setAddress(req.getAddress());
        store.setPhone(req.getPhone());
        return storeRepository.save(store);
    }

    @Override
    public void delete(String id) {
        Store store = getById(id);
        storeRepository.delete(store);
    }
}