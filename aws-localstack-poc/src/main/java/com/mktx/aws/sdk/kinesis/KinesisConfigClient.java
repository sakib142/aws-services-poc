package com.mktx.aws.sdk.kinesis;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class KinesisConfigClient {
    private static final Logger logger = LoggerFactory.getLogger(KinesisConfigClient.class);


    public AmazonKinesis kinesisClientBuilder(){
        AmazonKinesis client = AmazonKinesisClientBuilder.standard().
                withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://10.8.21.178:4568","us-east-1"))
                .build();
        return client;

    }


    private void createStreamOnKinesis (String streamName){
        AmazonKinesis client = kinesisClientBuilder();
        CreateStreamRequest createStreamRequest = new CreateStreamRequest();
        createStreamRequest.setStreamName(streamName);
        createStreamRequest.setShardCount(1);
        client.createStream( createStreamRequest );

    }

    private void listOfStream(String streamName){
        AmazonKinesis client = kinesisClientBuilder();
        ListStreamsResult listStreamsResult = client.listStreams();
        for(String listStream :listStreamsResult.getStreamNames() ){
            System.out.println("StreamName: "+listStream);
        }

    }

    private void pushToStreamSingleRecord(String awsStreamName, String partitionKey) {
        AmazonKinesis client = kinesisClientBuilder();
        PutRecordRequest request = new PutRecordRequest();
        request.setStreamName(awsStreamName);
        request.setPartitionKey(partitionKey);
        request.setData(ByteBuffer.wrap("Datastring".getBytes()));
        PutRecordResult result = client.putRecord(request);
        System.out.println(result);


    }

    private void pushToStream(String awsStreamName, String partitionKey) throws JsonProcessingException, UnsupportedEncodingException, InterruptedException, ExecutionException {
        AmazonKinesis client = kinesisClientBuilder();
        if (partitionKey == null || partitionKey.isEmpty()) {
            return;
        }

        PutRecordsRequest putRecordsRequest = new PutRecordsRequest();
        putRecordsRequest.setStreamName(awsStreamName);
        List<PutRecordsRequestEntry> putRecordsRequestEntryList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            PutRecordsRequestEntry putRecordsRequestEntry = new PutRecordsRequestEntry();
            putRecordsRequestEntry.setData(ByteBuffer.wrap(String.valueOf(i).getBytes()));
            putRecordsRequestEntry.setPartitionKey(partitionKey);
            putRecordsRequestEntryList.add(putRecordsRequestEntry);
        }

        putRecordsRequest.setRecords(putRecordsRequestEntryList);
        PutRecordsResult putRecordsResult = client.putRecords(putRecordsRequest);
        System.out.println("Put Result" + putRecordsResult);
        while (putRecordsResult.getFailedRecordCount() > 0) {
            final List<PutRecordsRequestEntry> failedRecordsList = new ArrayList<>();
            final List<PutRecordsResultEntry> putRecordsResultEntryList = putRecordsResult.getRecords();
            for (int i = 0; i < putRecordsResultEntryList.size(); i++) {
                final PutRecordsRequestEntry putRecordRequestEntry = putRecordsRequestEntryList.get(i);
                final PutRecordsResultEntry putRecordsResultEntry = putRecordsResultEntryList.get(i);
                if (putRecordsResultEntry.getErrorCode() != null) {
                    failedRecordsList.add(putRecordRequestEntry);
                }
            }
            putRecordsRequestEntryList = failedRecordsList;
            putRecordsRequest.setRecords(putRecordsRequestEntryList);
            putRecordsResult = client.putRecords(putRecordsRequest);
        }
    }
}
