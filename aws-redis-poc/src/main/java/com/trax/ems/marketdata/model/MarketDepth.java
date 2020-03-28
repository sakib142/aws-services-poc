package com.trax.ems.marketdata.model;

import java.io.Serializable;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
public class MarketDepth implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigInteger id;
    
    private String sourceVenue;

    private String sourceFeed;

    private String originatorId;

    private String originatorIdType;

    private BigInteger instId;

    private String side;

}
