package com.mktx.kinesis.emskinesis.handler;
import com.mktx.kinesis.emskinesis.bean.SearchQuery;
import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;
import org.springframework.http.ResponseEntity;

public class KinesisConsumeLambdaHandler extends SpringBootRequestHandler<SearchQuery, ResponseEntity<String>> {
}
