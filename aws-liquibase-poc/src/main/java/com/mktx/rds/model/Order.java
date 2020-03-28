package com.mktx.rds.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="ORDER_TEST")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="SOURCE")
    private String source;

    @Column(name="TARGET")
    private String target;

    @Column(name = "VENUE")
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
