package com.enigma.tokonyadia_api.dto.res;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartRes {
    private String stockId;

    private Integer quantity;
}