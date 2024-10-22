package com.enigma.tokonyadia_api.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordReq {
    @NotNull
    @NotBlank
    private String oldPassword;

    @NotNull
    @NotBlank
    private String newPassword;
}