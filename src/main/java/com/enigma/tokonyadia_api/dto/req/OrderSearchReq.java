package com.enigma.tokonyadia_api.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchReq extends PageReq {
    private String storeId;
    private String productId;
    private String customerId;
    private String voucherId;
    private String orderStatus;
    private String query;
}