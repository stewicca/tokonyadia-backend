package com.enigma.tokonyadia_api.dto.req;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PageReq {
    private Integer page;
    private Integer size;
    private String sort;

    public Integer getPage() {
        return page <= 0 ? 0 : page - 1;
    }
}