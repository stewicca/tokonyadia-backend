package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.entity.Store;
import com.enigma.tokonyadia_api.dto.req.StoreReq;

import java.util.List;

public interface StoreService {
    public List<Store> getAll();
    public Store getById(String id);
    public Store create(StoreReq req);
    public Store update(String id, StoreReq req);
    public void delete(String id);
}