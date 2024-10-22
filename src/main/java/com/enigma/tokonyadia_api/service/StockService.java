package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.dto.req.StockReq;
import com.enigma.tokonyadia_api.dto.req.StockSearchReq;
import com.enigma.tokonyadia_api.dto.res.StockRes;
import com.enigma.tokonyadia_api.entity.Stock;
import org.springframework.data.domain.Page;

public interface StockService {
    Page<StockRes> getAll(StockSearchReq req);

    StockRes getById(String id);

    Stock getOne(String id);

    StockRes create(StockReq req);

    void reduce(String id, Integer quantity);

    StockRes update(String id, StockReq req);

    void delete(String id);
}