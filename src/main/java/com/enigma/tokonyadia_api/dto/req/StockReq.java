package com.enigma.tokonyadia_api.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockReq {
    @NotNull
    @NotBlank
    private String storeId;

    @NotNull
    @NotBlank
    private String productId;

    @NotNull
    @Positive
    private Integer price;

    @NotNull
    @Positive
    private Integer stock;
}