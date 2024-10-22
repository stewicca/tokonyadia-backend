package com.enigma.tokonyadia_api.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRes extends BaseRes {
    private String name;

    private String description;

    private CategoryRes category;

    List<FileRes> images;
}