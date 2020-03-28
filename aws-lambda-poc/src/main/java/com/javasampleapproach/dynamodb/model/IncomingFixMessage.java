package com.javasampleapproach.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Data;

@Data
@DynamoDBTable(tableName = "GlobalIncomingFixMessage")
public class IncomingFixMessage {

	@DynamoDBHashKey
	@DynamoDBAutoGeneratedKey
	@DynamoDBAttribute(attributeName = "msgid")
	private String msgId;

	@DynamoDBAttribute(attributeName = "msgtext")
	private String msgText;

	@DynamoDBAttribute(attributeName = "timestamp")
	private String timeStamp;

}
