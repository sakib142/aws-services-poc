package com.trax.ems.cognito.lambdahandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.trax.ems.cognito.model.CognitoUser;
import com.trax.ems.cognito.service.CognitoCreateUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("CognitoCreateUser")
public class CognitoCreateUserlambda implements Function<DynamodbEvent, String> {

	@Autowired
	CognitoCreateUserService cognitoService;

	@Override
	public String apply(DynamodbEvent event) {
		log.info("Cognitolambda.apply()+");
		try {

			for (DynamodbStreamRecord stream : event.getRecords()) {
				log.info("Keys: " + stream.getDynamodb().getKeys());
				log.info("New Images: " + stream.getDynamodb().getNewImage());
				log.info("Old Images: " + stream.getDynamodb().getOldImage());
				Map<String, AttributeValue> partitionKeyMap = stream.getDynamodb().getKeys();
				List<String> partKeys = new ArrayList<String>();
				String cognitoTbPartitionKey = "";
				for (String partitionKey : partitionKeyMap.keySet()) {
					if (partitionKey.equals("username")) {
						cognitoTbPartitionKey = partitionKeyMap.get(partitionKey).getS();
						partKeys.add(cognitoTbPartitionKey);
						log.info("cognitoTbPartitionKey: " + cognitoTbPartitionKey);
					}
				}
				log.info("Read CognitoUser Information");
				CognitoUser user = cognitoService.readCognitoData(partKeys);
				log.info("Create CognitoUser");
				cognitoService.signUp(user);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Cognitolambda.apply()-");
		return "Lambda Execution Completed!!!";
	}
}
