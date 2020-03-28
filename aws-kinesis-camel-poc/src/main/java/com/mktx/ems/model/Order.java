package com.mktx.ems.model;

import lombok.Data;

@Data
public class Order {

    private Integer id;

    private String name;

    private String description;

    private String source;

    private String target;

    private String venue;
}
