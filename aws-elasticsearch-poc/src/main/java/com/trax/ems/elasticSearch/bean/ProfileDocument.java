package com.trax.ems.elasticSearch.bean;

import java.util.List;

import lombok.Data;

/*
 * Profile Document's field
 */
@Data
public class ProfileDocument {
    private String id;
    private String firstName;
    private String lastName;
    private List<String> emails;
}
