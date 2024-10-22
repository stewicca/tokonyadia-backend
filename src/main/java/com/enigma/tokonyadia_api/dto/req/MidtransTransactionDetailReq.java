package com.enigma.tokonyadia_api.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MidtransTransactionDetailReq {
    @JsonProperty(value = "order_id")
    private String orderId;

    @JsonProperty(value = "gross_amount")
    private Integer grossAmount;
}