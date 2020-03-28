package com.mktx.rds.model;

import lombok.Data;

/*
 * Request DTO contain a action which act while doing test with Lambda
 */

@Data
public class RequestDTO {

    private String action;
    private EmsInstrument emsInstrument;
    private Order order;
}
