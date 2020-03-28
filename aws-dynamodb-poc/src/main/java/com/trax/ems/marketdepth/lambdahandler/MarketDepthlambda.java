package com.trax.ems.marketdepth.lambdahandler;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.trax.ems.marketdepth.service.MarketDepthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Slf4j
@Component("MarketDepthlambda")
public class MarketDepthlambda implements Function<DynamodbEvent, String> {

	@Autowired
	MarketDepthService marketDepthService;

	@Override
	public String apply(DynamodbEvent event) {
		log.info("MarketDepthlambda.apply()+");
		marketDepthService.insertMkdepth();
		try {
			marketDepthService.fetchMarketDepthAll();
			marketDepthService.fetchMarketDepthByInstrument();
			marketDepthService.updateMarketDepth();
			marketDepthService.deleteMarketDepth();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("MarketDepthlambda.apply()-");
		return "ProcessCompleted!!!";
	}
}
