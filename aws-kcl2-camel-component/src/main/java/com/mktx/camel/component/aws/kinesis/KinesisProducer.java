/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mktx.camel.component.aws.kinesis;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.support.DefaultProducer;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;
import software.amazon.awssdk.services.kinesis.model.PutRecordResponse;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

public class KinesisProducer extends DefaultProducer {

    public KinesisProducer(KinesisEndpoint endpoint) {
        super(endpoint);
    }

    public static Message getMessageForResponse(final Exchange exchange) {
        return exchange.getMessage();
    }

    @Override
    public KinesisEndpoint getEndpoint() {
        return (KinesisEndpoint) super.getEndpoint();
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        PutRecordRequest request = createRequest(exchange);
        CompletableFuture<PutRecordResponse> future = getEndpoint().getClient().putRecord(request);
        if (future.isDone()) {
            PutRecordResponse putRecordResponse = future.get();
            Message message = getMessageForResponse(exchange);
            message.setHeader(KinesisConstants.SEQUENCE_NUMBER, putRecordResponse.sequenceNumber());
            message.setHeader(KinesisConstants.SHARD_ID, putRecordResponse.shardId());
        }
    }

    private PutRecordRequest createRequest(Exchange exchange) {
        ByteBuffer body = exchange.getIn().getBody(ByteBuffer.class);
        Object partitionKey = exchange.getIn().getHeader(KinesisConstants.PARTITION_KEY);
        Object sequenceNumber = exchange.getIn().getHeader(KinesisConstants.SEQUENCE_NUMBER);
        PutRecordRequest putRecordRequest = PutRecordRequest.builder()
                .partitionKey(partitionKey.toString())
                .streamName(getEndpoint().getConfiguration().getStreamName())
                .data(SdkBytes.fromByteArray(body.array())).build();

        if (sequenceNumber != null) {
            putRecordRequest.toBuilder().sequenceNumberForOrdering(sequenceNumber.toString());
        }
        return putRecordRequest;
    }
}
