package com.mktx.kinesis.emskinesis.controller;


import com.mktx.kinesis.emskinesis.bean.SearchQuery;
import com.mktx.kinesis.emskinesis.service.KinesisStreamConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.kinesis.model.Record;

import java.util.List;

@RestController
public class ConsumerController {

    @Value("${aws.stremName}")
    private String stremName;

    @Value("${aws.partionKey}")
    private String partionKey;

    @Value("${aws.access.key}")
    private String aws_access_key;

    @Value("${aws.secret.Key}")
    private String aws_secret_key;

    @Value("${aws.region}")
    private String aws_region;

    @Autowired
    KinesisStreamConsumerService kinesisStreamConsumerService;

    @GetMapping
    public ResponseEntity<String> getStreamDataByQuery(@RequestBody SearchQuery searchQuery){

       try{
           System.out.println("ConsumerController -- get mapping " );
           /*System.out.println("ConsumerController -- searchQuery" + searchQuery.getSequenceNum() +" --" + searchQuery.getShardId() );
           System.out.println(" OrderController stremName -- " + stremName + " ---- " + searchQuery.getShardIteratorType());
           System.out.println(" OrderController aws_access_key -- " + aws_access_key);
           System.out.println(" OrderController aws_secret_key -- " + aws_secret_key);*/
           List<Record> records = kinesisStreamConsumerService.getStreamData(stremName,partionKey,searchQuery);
       }catch(Exception e){
           e.printStackTrace();
       }
        return ResponseEntity.ok("Data fetched from kinesis.");
    }

}
