package com.enigma.tokonyadia_api.dto.res;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartCheckoutRes {
    private String orderId;

    private Integer amount;

    private String address;

    private String status;

    private String tokenSnap;

    private String redirectUrl;
}