package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.entity.Store;

import java.util.List;

public interface StoreService {
    public List<Store> getAll();
    public Store getById(String id);
    public Store create(Store store);
    public Store update(String id, Store store);
    public String delete(String id);
}