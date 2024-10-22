package com.enigma.tokonyadia_api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    CART("Cart"),
    PENDING("Pending"),
    CANCELED("Canceled"),
    CONFIRMED("Confirmed"),
    PROCESSING("Processing"),
    DELIVERED("Delivered"),
    COMPLETED("Completed");

    private final String name;

    public static OrderStatus getByName(String name) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getName().equalsIgnoreCase(name)) return orderStatus;
        }
        return null;
    }
}