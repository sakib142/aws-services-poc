package com.javasampleapproach.dynamodb.lambdahandler;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.javasampleapproach.dynamodb.service.IncomingFixMessageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("AWSLambdaPOC")
public class IncomingFixMessageLambda implements Function<DynamodbEvent, String> {

	@Autowired
	private IncomingFixMessageService incomingFixMessageService;
	private String singleGroupMsg = "8=FIXT.1.1|9=470|35=W|34=1667|49=MA_CPP|52=20180626-23:00:04.540|56=MA_EMS|24100=53944VAK5|24101=US53944VAK52|24102=USD|24103=USD_HGD|24110=1|24111=US53944VAK52_B_20180626225843000|24112=TICK|24113=U|24114=MA-PREDICT|24115=B|24116=20180626-22:58:43.000|24131=98.7518|24150=9128284T4|24151=US9128284T44|24155=68.54|22033=20180626|10=148|";

	@Override
	public String apply(DynamodbEvent event) {

		log.info("MarketDepthlambda.apply()+");
		incomingFixMessageService.setFixMessage(singleGroupMsg);
		log.info("MarketDepthlambda.apply()-");
		return "Data Inserted Succsefully to DynamoDB Table";
	}

}
