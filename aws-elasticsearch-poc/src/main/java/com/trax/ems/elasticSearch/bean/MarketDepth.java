package com.trax.ems.elasticSearch.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

import lombok.Data;

/**
 * MarketDepth DTO class.
 */
@Data
public class MarketDepth {

    private String id;

    private String uniqueBusinessKey;

    private String sourceVenue;

    private String sourceFeed;

    private String originatorId;

    private String originatorIdType;

    private BigInteger instId;

    private String side;

    private Instant sourceTimeStamp;

    private Instant updatedTimeStamp;

    private String originatorAcronym;

    private String isin;

    private String cusip;

    private Integer quoteTier;

    private Instant asOf;

    private String entryId;

    private String refEntryID;

    private String currency;

    private BigDecimal marketSize;

    private BigDecimal price;

    private BigDecimal yield;

    private BigDecimal yieldToNextCall;

    private Instant nextCallDate;

    private BigDecimal mmeYield;

    private BigDecimal discountMargin;

    private BigDecimal spread;

    private BigDecimal oasSpread;

    private BigDecimal ispread;

    private BigDecimal zspread;

    private BigDecimal aswSpread;

    private BigDecimal gspread;

    private String benchmarkIsin;

    private String benchmarkCusip;

    private String benchmarkCurveName;

    private String benchmarkCurvePoint;

    private String transactionType;

}
