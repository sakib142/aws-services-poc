package com.mktx.aws.sdk.kinesis;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

@Component
public class KinesisStreamConsumerService {

    private static GetShardIteratorRequest getShardIteratorRequest = null;


    public KinesisClient createSteamonKinesis()  {
        KinesisClient client = KinesisClient.builder().region(Region.of("us-east-1"))
                .endpointOverride(URI.create("http://10.8.21.178:4568"))
                .build();
        return client;
    }

    public List<Record> getStreamData(String stremName, String partionKey, SearchQuery searchQuery) {
        System.out.println("KinesisStreamConsumerService --- ");
        KinesisClient client = createSteamonKinesis();

//        GetShardIteratorRequest getShardIteratorRequest = GetShardIteratorRequest.builder().shardIteratorType(ShardIteratorType.AT_SEQUENCE_NUMBER).shardId(SHARD_ID).streamName(stremName).startingSequenceNumber("49596813507191112078064637726201122439686465933996458002").build();
//        GetShardIteratorRequest getShardIteratorRequest = GetShardIteratorRequest.builder().shardIteratorType(ShardIteratorType.TRIM_HORIZON).shardId(SHARD_ID).streamName(stremName).build();
        if (ShardIteratorType.AT_SEQUENCE_NUMBER.toString().equalsIgnoreCase(searchQuery.getShardIteratorType())){
            System.out.println("KinesisStreamConsumerService ---AT_SEQUENCE_NUMBER  ");
        }
        if (ShardIteratorType.TRIM_HORIZON.toString().equals(searchQuery.getShardIteratorType())) {
            getShardIteratorRequest = GetShardIteratorRequest.builder().shardIteratorType(searchQuery.getShardIteratorType()).shardId(searchQuery.getShardId()).streamName(stremName).build();
        } else if (ShardIteratorType.AT_SEQUENCE_NUMBER.toString().equals(searchQuery.getShardIteratorType())) {
            getShardIteratorRequest = GetShardIteratorRequest.builder().shardIteratorType(searchQuery.getShardIteratorType()).shardId(searchQuery.getShardId()).streamName(stremName).startingSequenceNumber(searchQuery.getSequenceNum()).build();
        }

        System.out.println("client  --- " + client);

        GetShardIteratorResponse shardREsponse = client.getShardIterator(getShardIteratorRequest);
        System.out.println("shardREsponse.shardIterator() --- " + shardREsponse.shardIterator());
        String shardIterator = shardREsponse.shardIterator();

        GetRecordsRequest getRecordsRequest = GetRecordsRequest.builder().shardIterator(shardIterator).limit(20).build();

        GetRecordsResponse getRecordsResult = client.getRecords(getRecordsRequest);
        List<Record> records = getRecordsResult.records();
        System.out.println("Records --- " + records);
        for (Record record : records) {
            System.out.println("record -- " + record.data().asString(Charset.forName("UTF-8")));
        }
        System.out.println("1st part size -- " + records.size() + "data--- " + records.get(0).data().asUtf8String() + "sequence nu -- " + records.get(0).sequenceNumber());


        return records;
    }
}
