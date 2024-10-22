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
public class PageReq {
    private Integer page;

    private Integer size;

    private String sort;

    private boolean isDeleted;

    public Integer getPage() {
        return page <= 0 ? 0 : page - 1;
    }
}