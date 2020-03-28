
package com.trax.ems.cognito.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.AttributeEncryptor;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.DynamoDBEncryptor;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.trax.ems.cognito.model.CognitoUser;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AwsDynamoDBEncryptor {

	@Value("${amazon.aws.region}")
	String region;

	@Value("${mktx.encrypt.key}")
	String encryptionKeyId;

	public CognitoUser decryptRecord(List<String> partitionKeys) {

		log.info("AwsDynamoDBEncryptor.decryptRecord()+");
		log.info("partitionKey: " + partitionKeys);
		log.info("encryptionKeyId: " + encryptionKeyId);
		log.info("region: " + region);

		CustomMaterialProvider cep = new CustomMaterialProvider(encryptionKeyId);
		final DynamoDBEncryptor encryptor = DynamoDBEncryptor.getInstance(cep);
		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();

		DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder().withSaveBehavior(SaveBehavior.PUT).build();
		DynamoDBMapper mapper = new DynamoDBMapper(ddb, mapperConfig, new AttributeEncryptor(encryptor));

		final Map<String, AttributeValue> itemKey = new HashMap<>();
		itemKey.put("username", new AttributeValue().withS(partitionKeys.get(0)));
		log.info("Encrypted Record: " + ddb.getItem("CognitoUser", itemKey).getItem());

		CognitoUser decrypted_record = mapper.load(CognitoUser.class, partitionKeys.get(0));

		log.info("Records After Decryyption:");
		log.info("UserName" + decrypted_record.getUsername());

		log.info("AwsDynamoDBEncryptor.decryptRecord()-");
		return decrypted_record;
	}
}
