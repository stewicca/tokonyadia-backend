package com.enigma.tokonyadia_api.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MidtransRes {
    @JsonProperty(value = "token")
    private String token;

    @JsonProperty(value = "redirect_url")
    private String redirectUrl;
}