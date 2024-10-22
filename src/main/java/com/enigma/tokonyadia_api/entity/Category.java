package com.enigma.tokonyadia_api.entity;

import com.enigma.tokonyadia_api.constant.Constant;
import jakarta.persistence.*;
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
@Entity
@Table(name = Constant.CATEGORY_TABLE)
public class Category extends BaseEntity {
    @Column(name = "category", nullable = false, unique = true)
    private String category;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<CategoryImage> images;

    @PrePersist
    protected void prePersist() {
        this.category = this.name.replaceAll(" ", "_").toUpperCase();
    }

    @PreUpdate
    protected void preUpdate() {
        this.category = this.name.replaceAll(" ", "_").toUpperCase();
    }
}