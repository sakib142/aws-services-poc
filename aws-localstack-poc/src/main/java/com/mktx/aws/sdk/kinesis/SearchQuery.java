package com.mktx.aws.sdk.kinesis;

import lombok.Data;

@Data
public class SearchQuery {

    private String shardId;
    private String sequenceNum;
    private String shardIteratorType;
}
