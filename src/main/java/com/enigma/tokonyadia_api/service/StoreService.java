package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.req.StoreReq;
import com.enigma.tokonyadia_api.dto.res.StoreRes;
import com.enigma.tokonyadia_api.entity.Store;
import org.springframework.data.domain.Page;

public interface StoreService {
    Page<StoreRes> getAll(PageReq req);

    StoreRes getById(String id);

    Store getOne(String id);

    StoreRes create(StoreReq req);

    StoreRes update(String id, StoreReq req);

    void delete(String id);
}