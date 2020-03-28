package com.mktx.order.svc.gradle.model;

public enum OrderStatus {
    NEW("ORDER_SINGLE"),
    OPEN(""),
    PENDING(""),
    CANCELLED(""),
    EXPIRED(""),
    COMPLETED(""),
    ERROR(""),
    PARTIAL_FILL(""),
    FILLED(""),
    REJECTED("");

    private final String name;

    private OrderStatus(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
