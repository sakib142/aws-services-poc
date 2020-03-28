package com.dynamo.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.dynamo.model.Order;


public class DynamoService {

    String tableName = "Order";

    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    DynamoDB dynamoDB = new DynamoDB(client);

    public void createItem(Order order){

        try {
            Table table = dynamoDB.getTable(tableName);
            System.out.println("Order : "+ order.toString());
            Item item = new Item().withPrimaryKey("ID", order.getId())
                    .withString("Name", order.getName())
                    .withString("Description", order.getDescription())
                    .withString("Source",order.getSource())
                    .withString("Target", order.getTarget())
                    .withString("Venue", order.getVenue());

            table.putItem(item);
        }
        catch (Exception e){
            System.err.println("Create items failed.");
            System.err.println(e.getMessage());
        }
    }
}
