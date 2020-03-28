package com.mktx.kinesis.emskinesis.bean;

import lombok.Data;

@Data
public class SearchQuery {

    private String shardId;
    private String sequenceNum;
    private String shardIteratorType;
}
