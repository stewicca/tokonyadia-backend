package com.enigma.tokonyadia_api.dto.req;

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
public class StockSearchReq extends PageReq {
    private String storeId;

    private String categoryId;

    private String query;
}