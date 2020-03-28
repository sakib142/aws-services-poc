package com.mktx.kinesis.emskinesis.handler;

import com.mktx.kinesis.emskinesis.bean.SearchQuery;
import com.mktx.kinesis.emskinesis.controller.ConsumerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class KinesisConsumeLambda implements Function<SearchQuery, String> {

    @Autowired
    private static ConsumerController consumerController;

    @Override
    public String apply(SearchQuery searchQuery) {
        /*System.out.println("Lambda function called");
        try{
            consumerController.getStreamDataByQuery(searchQuery);
        } catch(Exception e){
            e.printStackTrace();
        }*/
        return "Action completed successfully.";
    }
}
