package com.enigma.tokonyadia_api.dto.res;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Res<T> {
    private Integer status;
    private String message;
    private T data;
}