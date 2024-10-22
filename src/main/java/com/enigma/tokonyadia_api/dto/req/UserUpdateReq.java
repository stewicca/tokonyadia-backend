package com.enigma.tokonyadia_api.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateReq {
    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String roleId;
}