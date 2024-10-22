package com.enigma.tokonyadia_api.client;

import com.enigma.tokonyadia_api.dto.req.MidtransReq;
import com.enigma.tokonyadia_api.dto.res.MidtransRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "midtrans", url = "${midtrans.api.url}")
public interface MidtransClient {
    @PostMapping(
            path = "/snap/v1/transactions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    MidtransRes createSnapTransaction(
            @RequestBody MidtransReq req,
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String headerAuthorization
    );
}