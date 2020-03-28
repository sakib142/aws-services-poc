package com.trax.ems.marketdata.lambda;

import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

import com.trax.ems.marketdata.model.RequestDTO;
import com.trax.ems.marketdata.model.ResponseDTO;

public class MarketDataLambdaHandler extends SpringBootRequestHandler<RequestDTO, ResponseDTO> {

}
