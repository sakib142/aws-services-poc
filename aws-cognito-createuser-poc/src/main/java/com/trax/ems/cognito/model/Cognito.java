package com.trax.ems.cognito.model;

import java.math.BigInteger;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.DoNotEncrypt;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@DynamoDBTable(tableName = "CognitoUserInfo")
@Slf4j
public class Cognito {

	private String id;

	@DynamoDBHashKey
	@DynamoDBAttribute(attributeName = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@DynamoDBAttribute(attributeName = "instId")
	private BigInteger instId;

	@DynamoDBAttribute(attributeName = "asOf")
	private String asOf;

	@DynamoDBAttribute(attributeName = "currency")
	private String currency;

	@DynamoDBAttribute(attributeName = "cusip")
	private String cusip;

	@DynamoDBAttribute(attributeName = "SourceVenue")
	private String sourceVenue;

	@DynamoDBAttribute(attributeName = "SourceFeed")
	private String sourceFeed;

	@DynamoDBAttribute(attributeName = "OriginatorId")
	private String originatorId;

	@DynamoDBAttribute(attributeName = "OrigIdType")
	private String originatorIdType;

	@DynamoDBAttribute(attributeName = "Side")
	private String side;

	@DynamoDBAttribute(attributeName = "SourceTmstp")
	private String sourceTimeStamp;

	@DynamoDBAttribute(attributeName = "EntryId")
	private String entryId;

	@DynamoDBAttribute(attributeName = "Isin")
	private String isin;

	@DoNotEncrypt
	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	@DynamoDBAttribute(attributeName = "Quotetier")
	private Integer quoteTier;

	@DynamoDBAttribute(attributeName = "TransType")
	private String transactionType;

	@Override
	public boolean equals(Object obj) {
		Cognito mkd = (Cognito) obj;
		log.info("List Object, Feed" + this.getSourceFeed() + " Venue:" + this.getSourceVenue() + " OridId:"
				+ this.getOriginatorId() + " OrigType:" + this.getOriginatorIdType() + " Side:" + this.getSide()
				+ "INST:" + this.getInstId());

		log.info("Object, Feed" + mkd.getSourceFeed() + " Venue:" + mkd.getSourceVenue() + " OridId:"
				+ mkd.getOriginatorId() + " OrigType:" + mkd.getOriginatorIdType() + " Side:" + mkd.getSide() + "INST:"
				+ mkd.getInstId());

		return this.getSourceFeed().equals(mkd.getSourceFeed()) && this.getSourceVenue().equals(mkd.getSourceVenue())
				&& this.getInstId().equals(mkd.getInstId()) && this.getOriginatorId().equals(mkd.getOriginatorId())
				&& this.getOriginatorIdType().equals(mkd.getOriginatorIdType()) && this.getSide().equals(mkd.getSide());
	}

}
