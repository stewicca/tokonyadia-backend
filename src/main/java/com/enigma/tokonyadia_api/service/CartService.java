package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.dto.req.CartCheckoutReq;
import com.enigma.tokonyadia_api.dto.req.CartReq;
import com.enigma.tokonyadia_api.dto.req.CartUpdateReq;
import com.enigma.tokonyadia_api.dto.res.CartCheckoutRes;
import com.enigma.tokonyadia_api.dto.res.CartRes;

public interface CartService {
    CartRes add(String customerId, CartReq req);

    CartRes increase(String customerId, CartUpdateReq req);

    CartRes decrease(String customerId, CartUpdateReq req);

    void remove(String customerId, CartUpdateReq req);

    CartCheckoutRes checkout(String customerId, CartCheckoutReq req);
}