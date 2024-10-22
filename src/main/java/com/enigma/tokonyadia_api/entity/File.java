package com.enigma.tokonyadia_api.entity;

import com.enigma.tokonyadia_api.constant.Constant;
import jakarta.persistence.*;
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
@Entity
@Table(name = Constant.FILE_TABLE)
@Inheritance(strategy = InheritanceType.JOINED)
public class File extends BaseEntity {
    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size", nullable = false)
    private long size;
}