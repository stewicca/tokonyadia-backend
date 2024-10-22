package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.client.MidtransClient;
import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.constant.OrderStatus;
import com.enigma.tokonyadia_api.constant.PaymentStatus;
import com.enigma.tokonyadia_api.dto.req.MidtransNotificationReq;
import com.enigma.tokonyadia_api.dto.req.MidtransReq;
import com.enigma.tokonyadia_api.dto.req.MidtransTransactionDetailReq;
import com.enigma.tokonyadia_api.dto.res.MidtransRes;
import com.enigma.tokonyadia_api.entity.Order;
import com.enigma.tokonyadia_api.entity.Payment;
import com.enigma.tokonyadia_api.repository.PaymentRepository;
import com.enigma.tokonyadia_api.service.PaymentService;
import com.enigma.tokonyadia_api.service.StockService;
import com.enigma.tokonyadia_api.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final StockService stockService;
    private final MidtransClient midtransClient;
    private final PaymentRepository paymentRepository;

    @Value("${midtrans.server.key}")
    private String MIDTRANS_SERVER_KEY;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Payment create(Order order, int amount) {
        MidtransReq midtransReq = buildMidtransRequest(order, amount);
        String headerValue = createMidtransAuthHeader();

        MidtransRes snapTransaction = midtransClient.createSnapTransaction(midtransReq, headerValue);

        return savePayment(order, amount, snapTransaction);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void notification(MidtransNotificationReq req) {
        validateNotificationSignature(req);

        Payment payment = findPaymentByOrderId(req.getOrderId());

        updatePaymentStatus(payment, req);
        updateOrderStatusBasedOnPayment(payment);

        paymentRepository.save(payment);
    }

    private MidtransReq buildMidtransRequest(Order order, int amount) {
        return MidtransReq.builder()
                .transactionDetails(MidtransTransactionDetailReq.builder()
                        .orderId(order.getId())
                        .grossAmount(amount)
                        .build())
                .enabledPayments(List.of("bca_va", "gopay", "shopeepay", "other_qris"))
                .build();
    }

    private String createMidtransAuthHeader() {
        return "Basic " + Base64.getEncoder().encodeToString(MIDTRANS_SERVER_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private Payment savePayment(Order order, int amount, MidtransRes snapTransaction) {
        Payment payment = Payment.builder()
                .order(order)
                .amount(amount)
                .paymentStatus(PaymentStatus.PENDING)
                .tokenSnap(snapTransaction.getToken())
                .redirectUrl(snapTransaction.getRedirectUrl())
                .build();

        return paymentRepository.save(payment);
    }

    private void validateNotificationSignature(MidtransNotificationReq req) {
        if (!validateSignatureKey(req)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constant.ERROR_PAYMENT_GET_NOTIFICATION_MSG);
        }
    }

    private Payment findPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_ORDER_NOT_FOUND_MSG));
    }

    private void updatePaymentStatus(Payment payment, MidtransNotificationReq req) {
        PaymentStatus paymentStatus = PaymentStatus.getByName(req.getTransactionStatus());
        if (paymentStatus != null) {
            payment.setPaymentStatus(paymentStatus);
        }
    }

    private void updateOrderStatusBasedOnPayment(Payment payment) {
        switch (payment.getPaymentStatus()) {
            case SETTLEMENT:
                confirmOrder(payment.getOrder());
                break;
            case PENDING:
                break;
            default:
                cancelOrder(payment.getOrder());
        }
    }

    private void confirmOrder(Order order) {
        order.getOrderDetails().forEach(detail ->
                stockService.reduce(detail.getStock().getId(), detail.getQuantity()));
        order.setOrderStatus(OrderStatus.CONFIRMED);
    }

    private void cancelOrder(Order order) {
        order.setOrderStatus(OrderStatus.CANCELED);
    }

    private boolean validateSignatureKey(MidtransNotificationReq request) {
        String rawString = request.getOrderId() + request.getStatusCode() + request.getGrossAmount() + MIDTRANS_SERVER_KEY;
        String signatureKey = HashUtil.encryptThisString(rawString);
        return request.getSignatureKey().equalsIgnoreCase(signatureKey);
    }
}