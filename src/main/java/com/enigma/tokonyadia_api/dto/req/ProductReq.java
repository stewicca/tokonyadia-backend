package com.enigma.tokonyadia_api.dto.req;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReq {
    private String name;
    private String description;
}