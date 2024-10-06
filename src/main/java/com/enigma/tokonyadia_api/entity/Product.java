package com.enigma.tokonyadia_api.entity;

import lombok.*;
import jakarta.persistence.*;
import com.enigma.tokonyadia_api.constant.Constant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Constant.PRODUCT_TABLE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}