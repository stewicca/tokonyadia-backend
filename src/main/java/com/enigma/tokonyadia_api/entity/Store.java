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
@Table(name = Constant.STORE_TABLE)
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;
}