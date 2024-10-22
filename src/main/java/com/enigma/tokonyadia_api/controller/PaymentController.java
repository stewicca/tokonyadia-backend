package com.enigma.tokonyadia_api.controller;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.MidtransNotificationReq;
import com.enigma.tokonyadia_api.service.PaymentService;
import com.enigma.tokonyadia_api.util.ResUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = Constant.PAYMENT_API_URL)
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping(path = "/notifications")
    public ResponseEntity<?> handleNotification(@RequestBody MidtransNotificationReq midtransNotificationReq) {
        paymentService.notification(midtransNotificationReq);
        return ResUtil.buildRes(
                HttpStatus.OK,
                "OK",
                null);
    }
}