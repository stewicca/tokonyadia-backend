package com.enigma.tokonyadia_api.dto.res;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherRes extends BaseRes {
    private String code;

    private Integer discount;

    private String description;
}