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
@Table(name = Constant.VOUCHER_TABLE)
public class Voucher extends BaseEntity {
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "discount", nullable = false)
    private Integer discount;

    @Column(name = "description", nullable = false)
    private String description;
}