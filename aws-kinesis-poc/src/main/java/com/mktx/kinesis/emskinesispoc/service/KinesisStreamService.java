package com.mktx.kinesis.emskinesispoc.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import com.amazonaws.services.kinesis.producer.UserRecordFailedException;
import com.amazonaws.services.kinesis.producer.UserRecordResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
public class KinesisStreamService {
    private static final Logger logger = LoggerFactory.getLogger(KinesisStreamService.class);
    private KinesisProducer kinesisProducer = null;


/*//    @Value("${aws.access.key}")
    private String aws_access_key;// = "AKIA2QBGWXZSVSTUKKU6";

//    @Value("${aws.secret.key}")
    private String aws_secret_key;// = "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";

//    @Value("${aws.region}")
    private String aws_region;// = "us-east-2";*/

    private final AtomicLong recordsPut = new AtomicLong(0);

    /**
     * @return
     * @param aws_access_key
     * @param aws_secret_key
     * @param aws_region
     */
    private KinesisProducer getKinesisProducer(String aws_access_key, String aws_secret_key, String aws_region) {
        System.out.println("aws_access_key --- " + aws_access_key);
        if (kinesisProducer == null) {
            KinesisProducerConfiguration configuration = new KinesisProducerConfiguration();
            configuration.setRegion(aws_region);
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(aws_access_key, aws_secret_key);
            configuration.setCredentialsProvider(new AWSStaticCredentialsProvider(awsCredentials));
            configuration.setMaxConnections(1);
            configuration.setRequestTimeout(10000); // 6 Seconds
            configuration.setRecordMaxBufferedTime(10000); // 5 Seconds
            kinesisProducer = new KinesisProducer(configuration);
        }
        return kinesisProducer;
    }

    public void pushToStream(String awsStreamName, String partitionKey, String order, String aws_access_key, String aws_secret_key, String aws_region) throws IOException, ExecutionException, InterruptedException {
        System.out.println("aws_access_key --- " + aws_access_key);
        kinesisProducer = getKinesisProducer(aws_access_key,aws_secret_key,aws_region);
        if (partitionKey == null || partitionKey.isEmpty() || order == null || order.isEmpty() ) {
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("order.getData() before insert -- " + order);
        String payload = mapper.writeValueAsString(order);
        System.out.println("payload before insert -- " + payload);
        ByteBuffer data= ByteBuffer.wrap(payload.getBytes("UTF-8"));
        System.out.println("data ---- " + data);
        while (kinesisProducer.getOutstandingRecordsCount() > 1e4) {
            Thread.sleep(1);
        }

        recordsPut.incrementAndGet();
        ListenableFuture<UserRecordResult> listenableFuture = kinesisProducer.addUserRecord(awsStreamName, partitionKey,
                data);
        Futures.addCallback(listenableFuture, new FutureCallback<UserRecordResult>() {

            @Override
            public void onSuccess(UserRecordResult result) {
                logger.info("Succefully put data into Kinesis");
                System.out.println("result.getSequenceNumber() --- " + result.getSequenceNumber());
                System.out.println("result.getShardId() --- " + result.getShardId());
            }

            @Override
            public void onFailure(Throwable t) {
                logger.info("Failed to put data into Kinesis");
                t.printStackTrace();
                if (t instanceof UserRecordFailedException) {
                    UserRecordFailedException recordFailedException = (UserRecordFailedException) t;
                    UserRecordResult result = recordFailedException.getResult();
                    logger.info("Result {} ", result.isSuccessful());

                }
            }
        });
        System.out.println("kinesisProducer --- "  + kinesisProducer);
        System.out.println("Data may be inserted ---- " + kinesisProducer.getMetrics().iterator().next());
    }

}
