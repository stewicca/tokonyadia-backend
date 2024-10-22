package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.constant.OrderStatus;
import com.enigma.tokonyadia_api.dto.req.CartCheckoutReq;
import com.enigma.tokonyadia_api.dto.req.CartReq;
import com.enigma.tokonyadia_api.dto.req.CartUpdateReq;
import com.enigma.tokonyadia_api.dto.res.CartCheckoutRes;
import com.enigma.tokonyadia_api.dto.res.CartRes;
import com.enigma.tokonyadia_api.entity.*;
import com.enigma.tokonyadia_api.repository.OrderRepository;
import com.enigma.tokonyadia_api.service.*;
import com.enigma.tokonyadia_api.util.LogUtil;
import com.enigma.tokonyadia_api.util.MapperUtil;
import com.enigma.tokonyadia_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final StoreService storeService;
    private final StockService stockService;
    private final VoucherService voucherService;
    private final PaymentService paymentService;
    private final ValidationUtil validationUtil;
    private final CustomerService customerService;
    private final OrderRepository orderRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CartRes add(String customerId, CartReq req) {
        LogUtil.info("Adding product to cart");
        validationUtil.validate(req);

        Order order = findOrCreateCart(customerId, req.getStockId());

        OrderDetail orderDetail = findOrAddOrderDetail(order, req.getStockId(), req.getQuantity());

        orderRepository.saveAndFlush(order);
        LogUtil.info("Finished adding product to cart");
        return MapperUtil.toCartRes(orderDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CartRes increase(String customerId, CartUpdateReq req) {
        LogUtil.info("Increasing product quantity in cart");
        validationUtil.validate(req);

        Order order = getCartByCustomerId(customerId);
        OrderDetail orderDetail = getOrderDetail(order, req.getStockId());
        orderDetail.setQuantity(orderDetail.getQuantity() + 1);

        orderRepository.saveAndFlush(order);
        LogUtil.info("Finished increasing product quantity in cart");
        return MapperUtil.toCartRes(orderDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CartRes decrease(String customerId, CartUpdateReq req) {
        LogUtil.info("Decreasing product quantity in cart");
        validationUtil.validate(req);

        Order order = getCartByCustomerId(customerId);
        OrderDetail orderDetail = getOrderDetail(order, req.getStockId());

        if (orderDetail.getQuantity() > 1) {
            orderDetail.setQuantity(orderDetail.getQuantity() - 1);
        } else {
            order.getOrderDetails().remove(orderDetail);
        }

        orderRepository.saveAndFlush(order);
        LogUtil.info("Finished decreasing product quantity in cart");
        return MapperUtil.toCartRes(orderDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(String customerId, CartUpdateReq req) {
        LogUtil.info("Removing product from cart");
        validationUtil.validate(req);

        Order order = getCartByCustomerId(customerId);
        OrderDetail orderDetail = getOrderDetail(order, req.getStockId());
        order.getOrderDetails().remove(orderDetail);

        orderRepository.saveAndFlush(order);
        LogUtil.info("Finished removing product from cart");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CartCheckoutRes checkout(String customerId, CartCheckoutReq req) {
        LogUtil.info("Checking out products from cart");
        validationUtil.validate(req);

        Order order = getCartByCustomerId(customerId);

        int totalAmount = calculateTotalAmount(order);

        if (req.getVoucher() != null && !req.getVoucher().isEmpty()) {
            totalAmount = applyVoucher(req.getVoucher(), totalAmount);
        }

        Payment payment = paymentService.create(order, totalAmount);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setAddress(req.getAddress());

        orderRepository.saveAndFlush(order);
        LogUtil.info("Finished checkout process");
        return MapperUtil.toCartCheckoutRes(payment);
    }

    @Transactional(rollbackFor = Exception.class)
    protected Order findOrCreateCart(String customerId, String stockId) {
        Stock stock = stockService.getOne(stockId);
        String storeId = stock.getStore().getId();

        Order order = orderRepository.findOrderByCustomerIdAndOrderStatus(customerId, OrderStatus.CART)
                .orElseGet(() -> createCart(customerId, storeId));

        if (!storeId.equals(order.getStore().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constant.ERROR_STORE_ID_NOT_MATCH_MSG);
        }

        return order;
    }

    private OrderDetail findOrAddOrderDetail(Order order, String stockId, int quantity) {
        Stock stock = stockService.getOne(stockId);

        return order.getOrderDetails().stream()
                .filter(detail -> detail.getStock().getId().equals(stock.getId()))
                .findFirst()
                .map(orderDetail -> {
                    orderDetail.setQuantity(orderDetail.getQuantity() + quantity);
                    orderDetail.setPrice(stock.getPrice());
                    return orderDetail;
                })
                .orElseGet(() -> {
                    OrderDetail newOrderDetail = OrderDetail.builder()
                            .order(order)
                            .stock(stock)
                            .quantity(quantity)
                            .price(stock.getPrice())
                            .build();
                    order.getOrderDetails().add(newOrderDetail);
                    return newOrderDetail;
                });
    }

    private int calculateTotalAmount(Order order) {
        return order.getOrderDetails().stream()
                .mapToInt(detail -> detail.getPrice() * detail.getQuantity())
                .sum();
    }

    private int applyVoucher(String voucherCode, int amount) {
        Voucher voucher = voucherService.getByCode(voucherCode);
        return amount * (100 - voucher.getDiscount()) / 100;
    }

    private Order getCartByCustomerId(String customerId) {
        return orderRepository.findOrderByCustomerIdAndOrderStatus(customerId, OrderStatus.CART)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_CART_NOT_FOUND_MSG));
    }

    private OrderDetail getOrderDetail(Order order, String stockId) {
        return order.getOrderDetails().stream()
                .filter(detail -> detail.getStock().getId().equals(stockId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_ITEM_NOT_FOUND_IN_CART_MSG));
    }

    @Transactional(rollbackFor = Exception.class)
    protected Order createCart(String customerId, String storeId) {
        Order order = Order.builder()
                .customer(customerService.getOne(customerId))
                .store(storeService.getOne(storeId))
                .orderStatus(OrderStatus.CART)
                .orderDetails(new ArrayList<>())
                .build();
        return orderRepository.save(order);
    }
}