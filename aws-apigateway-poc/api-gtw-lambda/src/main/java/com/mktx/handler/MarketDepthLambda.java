package com.mktx.handler;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mktx.model.MarketDepth;
import com.mktx.model.RequestInput;
import com.mktx.model.ResponseOutput;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class MarketDepthLambda  implements Function<APIGatewayProxyRequestEvent, ResponseOutput> {

	static Map<String, MarketDepth> mDepth = new HashMap<>();

	static {
		mDepth.put("1", new MarketDepth(BigInteger.ONE, "MKTX11", "X", "TRAX","O", BigInteger.ZERO, "S"));
		mDepth.put("2", new MarketDepth(BigInteger.valueOf(12L), "TRAX12", "Y", "TRAX","O", BigInteger.valueOf(1L), "S"));
		mDepth.put("3", new MarketDepth(BigInteger.valueOf(13L), "MKTX13", "Z", "TRAX","O", BigInteger.valueOf(2L), "S"));
		mDepth.put("4", new MarketDepth(BigInteger.valueOf(14L), "TRAX14", "A", "TRAX","O", BigInteger.valueOf(3L), "S"));
		mDepth.put("5", new MarketDepth(BigInteger.valueOf(15L), "MKTX15", "B", "TRAX","O", BigInteger.valueOf(4L), "S"));
	}


	@Override
	public ResponseOutput apply(APIGatewayProxyRequestEvent req) {
		// TODO Auto-generated method stub
		log.info("input request context received:{} ", req.getRequestContext());
		log.info("input body received:{}  ", req.getBody());

		ObjectMapper mapper = new ObjectMapper();
		RequestInput inputReceived = null;
		ResponseOutput outputResponse = new ResponseOutput();
		try {
			inputReceived = mapper.readValue(req.getBody(), RequestInput.class);

			log.info("input Instid:{}  ", inputReceived.getData());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			outputResponse.setBody(mapper.writeValueAsString(mDepth.get(inputReceived.getData())));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return outputResponse;
	}

}