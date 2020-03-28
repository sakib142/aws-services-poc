package com.mktx.model;

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

	public MarketDepth(BigInteger id, String sourceVenue, String sourceFeed, String originatorId,
			String originatorIdType, BigInteger instId, String side) {
		super();
		this.id = id;
		this.sourceVenue = sourceVenue;
		this.sourceFeed = sourceFeed;
		this.originatorId = originatorId;
		this.originatorIdType = originatorIdType;
		this.instId = instId;
		this.side = side;
	}

}
