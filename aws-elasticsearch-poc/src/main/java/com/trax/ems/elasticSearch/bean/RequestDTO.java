package com.trax.ems.elasticSearch.bean;

import lombok.Data;

/*
 * Request DTO contain a action which act while doing test with Lambda
 */

@Data
public class RequestDTO {

    private String action;
    private ProfileDocument profileDocument;
}
