package com.enigma.tokonyadia_api.dto.req;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReq {
    private String name;
    private String address;
    private String phone;
    private String email;
}