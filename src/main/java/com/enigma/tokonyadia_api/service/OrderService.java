package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.dto.req.OrderCreateReq;
import com.enigma.tokonyadia_api.dto.req.OrderSearchReq;
import com.enigma.tokonyadia_api.dto.req.OrderUpdateReq;
import com.enigma.tokonyadia_api.dto.res.OrderRes;
import com.enigma.tokonyadia_api.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    Page<OrderRes> getAll(OrderSearchReq req);

    OrderRes getById(String id);

    Order getOne(String id);

    OrderRes create(OrderCreateReq req);

    OrderRes update(String id, OrderUpdateReq req);

    void delete(String id);
}