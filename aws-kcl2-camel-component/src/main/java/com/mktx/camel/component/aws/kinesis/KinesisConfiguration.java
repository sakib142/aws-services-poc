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

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;
import org.apache.camel.spi.UriPath;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.kinesis.common.InitialPositionInStream;
import software.amazon.kinesis.metrics.MetricsLevel;

@UriParams
public class KinesisConfiguration implements Cloneable {

    @UriPath(description = "Name of the stream")
    @Metadata(required = true)
    private String streamName;
    @UriParam(description = "Name of the application, streamName will be used in case not provided. KCL2 will create the DynamoDB table same as applicationName")
    private String applicationName;
    @UriParam(enums = "NONE, DETAILED, SUMMARY", defaultValue = "NONE", description = "Cloudwatch metrics level configuration")
    private MetricsLevel metricsLevel = MetricsLevel.NONE;
    @UriParam(enums = "HTTP1_1,HTTP2", defaultValue = "HTTP1_1", description = "To define a protocol used by KCL2 when communicating with Kinesis")
    private software.amazon.awssdk.http.Protocol protocol = software.amazon.awssdk.http.Protocol.HTTP1_1;
    @UriParam(defaultValue = "false", description = "To enable/disable Enhanced fan-out, "
            + "KCL2 registers consumer to receive data from streams if enabled or uses poll strategy by using GetShardIterator & GetRecords API")
    private boolean fanout = false;
    @UriParam(label = "security", secret = true, description = "Amazon AWS Access Key")
    private String accessKey;
    @UriParam(label = "security", secret = true, description = "Amazon AWS Secret Key")
    private String secretKey;
    @UriParam(description = "The region in which Kinesis client needs to work. When using this parameter, the configuration will expect the capitalized name of the region (for example AP_EAST_1)"
            + "You'll need to use the name Regions.EU_WEST_1.name()")
    private String region;
    @UriParam(description = "Amazon Kinesis client to use for all requests for this endpoint")
    private KinesisAsyncClient amazonKinesisClient;
    @UriParam(label = "consumer", description = "Maximum number of records that will be fetched in each poll", defaultValue = "1")
    private int maxResultsPerRequest = 1;
    @UriParam(label = "consumer", description = "Defines where in the Kinesis stream to start getting records", defaultValue = "TRIM_HORIZON")
    private InitialPositionInStream iteratorType = InitialPositionInStream.TRIM_HORIZON;
    @UriParam(label = "consumer", description = "Defines which shardId in the Kinesis stream to get records from")
    private String shardId = "";
    @UriParam(label = "consumer", description = "The sequence number to start polling from. Required if iteratorType is set to AFTER_SEQUENCE_NUMBER or AT_SEQUENCE_NUMBER")
    private String sequenceNumber = "";
    @UriParam(label = "consumer", defaultValue = "ignore", description = "Define what will be the behavior in case of shard closed. Possible value are ignore, silent and fail."
            + " In case of ignore a message will be logged and the consumer will restart from the beginning,"
            + "in case of silent there will be no logging and the consumer will start from the beginning,"
            + "in case of fail a ReachedClosedStateException will be raised")
    private KinesisShardClosedStrategyEnum shardClosed;

    public KinesisAsyncClient getAmazonKinesisClient() {
        return amazonKinesisClient;
    }

    public void setAmazonKinesisClient(KinesisAsyncClient amazonKinesisClient) {
        this.amazonKinesisClient = amazonKinesisClient;
    }

    public int getMaxResultsPerRequest() {
        return maxResultsPerRequest;
    }

    public void setMaxResultsPerRequest(int maxResultsPerRequest) {
        this.maxResultsPerRequest = maxResultsPerRequest;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public MetricsLevel getMetricsLevel() {
        return metricsLevel;
    }

    public void setMetricsLevel(MetricsLevel metricsLevel) {
        this.metricsLevel = metricsLevel;
    }

    public software.amazon.awssdk.http.Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(software.amazon.awssdk.http.Protocol protocol) {
        this.protocol = protocol;
    }

    public boolean isFanout() {
        return fanout;
    }

    public void setFanout(boolean fanout) {
        this.fanout = fanout;
    }

    public InitialPositionInStream getIteratorType() {
        return iteratorType;
    }

    public void setIteratorType(InitialPositionInStream iteratorType) {
        this.iteratorType = iteratorType;
    }

    public String getShardId() {
        return shardId;
    }

    public void setShardId(String shardId) {
        this.shardId = shardId;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public KinesisShardClosedStrategyEnum getShardClosed() {
        return shardClosed;
    }

    public void setShardClosed(KinesisShardClosedStrategyEnum shardClosed) {
        this.shardClosed = shardClosed;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    // *************************************************
    //
    // *************************************************

    public KinesisConfiguration copy() {
        try {
            return (KinesisConfiguration) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeCamelException(e);
        }
    }
}
