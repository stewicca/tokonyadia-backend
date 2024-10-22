package com.enigma.tokonyadia_api.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateReq {
    @NotNull
    @NotBlank
    private String storeId;

    @NotNull
    private List<OrderDetailReq> orderDetails;
}