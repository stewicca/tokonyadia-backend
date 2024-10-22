package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.dto.req.MidtransNotificationReq;
import com.enigma.tokonyadia_api.entity.Order;
import com.enigma.tokonyadia_api.entity.Payment;

public interface PaymentService {
    Payment create(Order order, int amount);
    void notification(MidtransNotificationReq req);
}