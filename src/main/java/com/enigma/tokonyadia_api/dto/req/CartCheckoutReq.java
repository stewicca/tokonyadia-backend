package com.enigma.tokonyadia_api.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartCheckoutReq {
    @NotBlank(message = "Address cannot be empty")
    private String address;

    private String voucher;
}