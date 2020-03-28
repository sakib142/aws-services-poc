package com.mktx.cognito.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@DynamoDBTable(tableName = "CognitoUser")
public class CognitoUser {

	@DynamoDBHashKey
	@DynamoDBAttribute(attributeName = "username")
	private String username;

	@DynamoDBAttribute(attributeName = "name")
	private String name;

	@DynamoDBAttribute(attributeName = "userCreatedDate")
	private String userCreatedDate;

	@DynamoDBAttribute(attributeName = "lastModifiedDate")
	private String lastModifiedDate;

	@DynamoDBAttribute(attributeName = "enabled")
	private Boolean enabled;

	@DynamoDBAttribute(attributeName = "userStatus")
	private String userStatus;

	@DynamoDBAttribute(attributeName = "password")
	private String password;

	@DynamoDBAttribute(attributeName = "email")
	private String email;

	@DynamoDBAttribute(attributeName = "firm")
	private String firm;

	@DynamoDBAttribute(attributeName = "phonenumber")
	private String phonenumber;

}
