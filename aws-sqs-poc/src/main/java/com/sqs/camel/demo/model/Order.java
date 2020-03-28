package com.sqs.camel.demo.model;

import lombok.Data;

@Data
public class Order {

    private Integer id;

    private String name;

    private String description;

    private String source;

    private String target;

    private String venue;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", venue='" + venue + '\'' +
                '}';
    }
}
