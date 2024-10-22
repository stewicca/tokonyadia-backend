package com.enigma.tokonyadia_api.dto.res;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRes {
    private String userId;

    private String role;

    private String token;

    private String refreshToken;
}