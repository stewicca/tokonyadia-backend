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
@Table(name = Constant.ROLE_TABLE)
public class Role extends BaseEntity {
    @Column(name = "role", nullable = false, unique = true)
    private String role;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @PrePersist
    protected void prePersist() {
        this.role = String.format("ROLE_%s", this.name.replaceAll(" ", "_").toUpperCase());
    }

    @PreUpdate
    protected void preUpdate() {
        this.role = String.format("ROLE_%s", this.name.replaceAll(" ", "_").toUpperCase());
    }
}