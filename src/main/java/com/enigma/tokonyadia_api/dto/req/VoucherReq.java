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
public class VoucherReq {
    @NotNull
    @NotBlank
    private String code;

    @NotNull
    @Positive
    private Integer discount;

    @NotNull
    @NotBlank
    private String description;
}