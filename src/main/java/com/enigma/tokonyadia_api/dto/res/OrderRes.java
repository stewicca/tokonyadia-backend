package com.enigma.tokonyadia_api.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRes extends BaseRes {
    private CustomerRes customer;

    private StoreRes store;

    private String address;

    private String voucher;

    private String status;

    private List<OrderDetailRes> orderDetails;

    private PaymentRes payment;
}