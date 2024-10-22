package com.enigma.tokonyadia_api.util;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.res.*;
import com.enigma.tokonyadia_api.entity.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MapperUtil {
    public static RoleRes toRoleRes(Role role) {
        return RoleRes.builder()
                .id(role.getId())
                .name(role.getName())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }

    public static UserRes toUserRes(User user) {
        return UserRes.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static CustomerRes toCustomerRes(Customer customer) {
        UserRes user = toUserRes(customer.getUser());
        List<CustomerAddressRes> addresses = customer.getAddresses().stream().map(MapperUtil::toCustomerAddressRes).toList();
        return CustomerRes.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .addresses(addresses)
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .user(user)
                .build();
    }

    public static CustomerAddressRes toCustomerAddressRes(CustomerAddress customerAddress) {
        return CustomerAddressRes.builder()
                .id(customerAddress.getId())
                .address(customerAddress.getAddress())
                .createdAt(customerAddress.getCreatedAt())
                .updatedAt(customerAddress.getUpdatedAt())
                .build();
    }

    public static AuthRes toAuthRes(User user, String token, String refreshToken) {
        return AuthRes.builder()
                .userId(user.getId())
                .role(user.getRole().getName())
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    public static FileRes toFileRes(File file, String type) {
        return FileRes.builder()
                .id(file.getId())
                .url(Constant.IMAGE_API_URL + "/" + type + "/" + file.getId())
                .build();
    }

    public static CategoryRes toCategoryRes(Category category) {
        return CategoryRes.builder()
                .id(category.getId())
                .name(category.getName())
                .images(category.getImages() != null && !category.getImages().isEmpty()
                        ? category.getImages().stream().map(file -> toFileRes(file, "category")).toList()
                        : Collections.emptyList()
                )
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public static ProductRes toProductRes(Product product) {
        CategoryRes category = toCategoryRes(product.getCategory());
        return ProductRes.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(category)
                .images(product.getImages() != null && !category.getImages().isEmpty()
                        ? product.getImages().stream().map(file -> toFileRes(file, "product")).toList()
                        : Collections.emptyList()
                )
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static StoreRes toStoreRes(Store store) {
        return StoreRes.builder()
                .id(store.getId())
                .siup(store.getSiup())
                .name(store.getName())
                .phone(store.getPhone())
                .address(store.getAddress())
                .createdAt(store.getCreatedAt())
                .updatedAt(store.getUpdatedAt())
                .build();
    }

    public static StockRes toStockRes(Stock stock) {
        StoreRes store = toStoreRes(stock.getStore());
        ProductRes product = toProductRes(stock.getProduct());
        return StockRes.builder()
                .id(stock.getId())
                .store(store)
                .product(product)
                .price(stock.getPrice())
                .stock(stock.getStock())
                .createdAt(stock.getCreatedAt())
                .updatedAt(stock.getUpdatedAt())
                .build();
    }

    public static VoucherRes toVoucherRes(Voucher voucher) {
        return VoucherRes.builder()
                .id(voucher.getId())
                .code(voucher.getCode())
                .discount(voucher.getDiscount())
                .description(voucher.getDescription())
                .createdAt(voucher.getCreatedAt())
                .updatedAt(voucher.getUpdatedAt())
                .build();
    }

    public static CartRes toCartRes(OrderDetail orderDetail) {
        return CartRes.builder()
                .stockId(orderDetail.getStock().getId())
                .quantity(orderDetail.getQuantity())
                .build();
    }

    public static CartCheckoutRes toCartCheckoutRes(Payment payment) {
        return CartCheckoutRes.builder()
                .orderId(payment.getOrder().getId())
                .amount(payment.getAmount())
                .address(payment.getOrder().getAddress())
                .status(payment.getPaymentStatus().getName())
                .tokenSnap(payment.getTokenSnap())
                .redirectUrl(payment.getRedirectUrl())
                .build();
    }

    public static OrderRes toOrderRes(Order order) {
        Optional<Customer> customer = Optional.ofNullable(order.getCustomer());
        StoreRes store = toStoreRes(order.getStore());
        List<OrderDetailRes> details = order.getOrderDetails().stream().map(MapperUtil::toOrderDetailRes).toList();
        Optional<Payment> payment = Optional.ofNullable(order.getPayment());
        Optional<Voucher> voucher = Optional.ofNullable(order.getVoucher());
        return OrderRes.builder()
                .id(order.getId())
                .customer(customer.map(MapperUtil::toCustomerRes).orElse(null))
                .store(store)
                .address(order.getAddress())
                .voucher(voucher.map(Voucher::getCode).orElse(null))
                .status(order.getOrderStatus().getName())
                .orderDetails(details)
                .payment(payment.map(MapperUtil::toPaymentRes).orElse(null))
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    public static OrderDetailRes toOrderDetailRes(OrderDetail orderDetail) {
        return OrderDetailRes.builder()
                .id(orderDetail.getId())
                .product(orderDetail.getStock().getProduct().getName())
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getPrice())
                .createdAt(orderDetail.getCreatedAt())
                .updatedAt(orderDetail.getUpdatedAt())
                .build();
    }

    public static PaymentRes toPaymentRes(Payment payment) {
        return PaymentRes.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .status(payment.getPaymentStatus().getName())
                .tokenSnap(payment.getTokenSnap())
                .redirectUrl(payment.getRedirectUrl())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}