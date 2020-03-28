package com.trax.lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KinesisDynamoDBHandler implements RequestHandler<KinesisEvent, Void> {

    // Initialize the Log4j logger.
    private static final Logger logger
            = LoggerFactory.getLogger(KinesisDynamoDBHandler.class);

    private AmazonDynamoDB client;

    public KinesisDynamoDBHandler() {
        logger.info("========= Cold Start ==========");
        long start = System.currentTimeMillis();
        client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1.getName()).build();
        long end = System.currentTimeMillis();
        long duration = (end - start) / 1000;
        logger.debug("======== DynamoDB Client Build Completed! Took " + duration + " Seconds ===========");
    }

    @Override
    public Void handleRequest(KinesisEvent event, Context context) {
        logger.info("Received " + event.getRecords().size() + " raw Event Records!");

        // Stream the Records from the Lambda Event
        event.getRecords().stream().forEach(record -> {
            System.out.println(record.getKinesis().getData().array());
        });

        client.listTables().getTableNames().forEach(i -> logger.info(i));

        return null;
    }

    public static void main(String[] args) {
        KinesisDynamoDBHandler test = new KinesisDynamoDBHandler();
        test.client.listTables().getTableNames().forEach(i -> logger.info(i));
    }


}
