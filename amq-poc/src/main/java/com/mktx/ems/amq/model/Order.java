package com.mktx.ems.amq.model;

import lombok.Data;

import java.io.Serializable;


@Data
public class Order implements Serializable{

    private Long id;

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
