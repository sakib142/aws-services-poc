
package com.mktx.cognito.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.AttributeEncryptor;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.DynamoDBEncryptor;
import com.mktx.cognito.model.CognitoUser;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AwsDynamoDBEncryptor {

	@Value("${amazon.aws.region}")
	String region;

	@Value("${mktx.cognito.encrypt.key}")
	String encryptionKeyId;

	public void encryptRecord(CognitoUser cognito) {
		log.info("AwsDynamoDBEncryptor.encryptRecord()+");
		String partitionKey = cognito.getUsername();
		log.info("PartitionKey: " + partitionKey);
		log.info("encryptionKeyId: " + encryptionKeyId);
		log.info("region: " + region);

		CustomMaterialProvider cep = new CustomMaterialProvider(encryptionKeyId);
		final DynamoDBEncryptor encryptor = DynamoDBEncryptor.getInstance(cep);
		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
		DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder().withSaveBehavior(SaveBehavior.PUT).build();
		DynamoDBMapper mapper = new DynamoDBMapper(ddb, mapperConfig, new AttributeEncryptor(encryptor));

		log.info("Plaintext Record: " + cognito);
		log.info("Mapper;" + mapper.getTableModel(CognitoUser.class).fields().toArray());
		log.info(mapper.getTableModel(CognitoUser.class).field("username").name());
		mapper.save(cognito);
		log.info("encryptRecord saved !!!");
		log.info("AwsDynamoDBEncryptor.encryptRecord()-");
	}
}
