package com.enigma.tokonyadia_api.dto.res;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private Long total;
    private Integer pages;
    private Integer page;
    private Integer size;
}