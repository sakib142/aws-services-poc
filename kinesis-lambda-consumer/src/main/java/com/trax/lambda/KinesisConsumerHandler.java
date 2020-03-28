package com.trax.lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;


public class KinesisConsumerHandler implements RequestHandler<KinesisEvent, Void> {

    public KinesisConsumerHandler() {
        System.out.println("========= Cold Start ==========");
    }

    @Override
    public Void handleRequest(KinesisEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Received " + event.getRecords().size() + " raw Event Records!");

        // Stream the Records from the Lambda Event
        event.getRecords().stream().forEach(record -> {
            logger.log(record.getKinesis().getData().array());
        });

        return null;
    }


}
