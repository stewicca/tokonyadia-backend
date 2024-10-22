package com.enigma.tokonyadia_api.entity;

import com.enigma.tokonyadia_api.constant.Constant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = Constant.STORE_TABLE)
public class Store extends BaseEntity {
    @Column(name = "siup", nullable = false)
    private String siup;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;
}