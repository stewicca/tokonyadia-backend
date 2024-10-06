package com.enigma.tokonyadia_api.dto.res;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PageRes<T> extends Res<T> {
    private PageInfo pagination;
}