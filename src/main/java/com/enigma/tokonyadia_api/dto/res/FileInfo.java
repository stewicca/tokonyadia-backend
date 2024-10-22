package com.enigma.tokonyadia_api.dto.res;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    private String filename;

    private String path;
}