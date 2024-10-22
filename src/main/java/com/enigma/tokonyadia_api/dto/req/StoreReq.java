package com.enigma.tokonyadia_api.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreReq {
    @NotNull
    @NotBlank
    private String siup;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String phone;

    @NotNull
    @NotBlank
    private String address;
}