package com.enigma.tokonyadia_api.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MidtransReq {
    @JsonProperty(value = "transaction_details")
    private MidtransTransactionDetailReq transactionDetails;

    @JsonProperty(value = "enabled_payments")
    private List<String> enabledPayments;
}