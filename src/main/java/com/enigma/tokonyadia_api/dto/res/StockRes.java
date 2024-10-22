package com.enigma.tokonyadia_api.dto.res;

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
public class StockRes extends BaseRes {
    private StoreRes store;

    private ProductRes product;

    private Integer price;

    private Integer stock;
}