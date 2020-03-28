package com.trax.ems.marketdepth.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.trax.ems.marketdepth.model.MarketDepth;
import com.trax.ems.marketdepth.repository.MarketDepthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MarketDepthService {

	@Autowired
	private MarketDepthRepository marketDepthRepository;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	public void createTable(String tableName){

		CreateTableRequest createTableRequest = new CreateTableRequest()
				.withTableName(tableName)
				.withKeySchema(new KeySchemaElement("ID", KeyType.HASH))
				.withAttributeDefinitions(new AttributeDefinition("ID", ScalarAttributeType.S))
				//.withAttributeDefinitions(new AttributeDefinition("instId", ScalarAttributeType.S))
				.withProvisionedThroughput(new ProvisionedThroughput(5L, 5L));

		amazonDynamoDB.createTable(createTableRequest);
	}

	public void insertMkdepth(){
		List<MarketDepth> listMarketDepths = new ArrayList<>();
		for(int i=0; i<10; i++){
			MarketDepth marketDepth=new MarketDepth();
			marketDepth.setEntryId("31ad");
			marketDepth.setAsOf("3 Jan");
			marketDepth.setCurrency("USD");
			marketDepth.setCusip("111111111");
			marketDepth.setInstId(new BigInteger("235"));
			marketDepth.setIsin("222222");
			marketDepth.setOriginatorId("215465");
			marketDepth.setOriginatorIdType("type");
			marketDepth.setQuoteTier(2454);
			marketDepth.setSide("side");
			marketDepth.setSourceFeed("feed");
			marketDepth.setSourceVenue("MKTX");
			marketDepth.setSourceTimeStamp("02081992");
			listMarketDepths.add(marketDepth);

		}
		marketDepthRepository.saveAll(listMarketDepths);

	}

	public void fetchMarketDepthAll() throws Exception {
		log.info("MarketDepthService.fetchMarketDepth()+");
		Iterable<MarketDepth> marketDepthAll = marketDepthRepository.findAll();
		for (MarketDepth mkd : marketDepthAll) {
			log.info("ID:" + mkd.getId());
			log.info("INST_ID:" + mkd.getInstId());
			log.info("CURRENCY:" + mkd.getCurrency());
			log.info("CUSIP:" + mkd.getCusip());
			log.info("AsOf:" + mkd.getAsOf());
		}
		log.info("MarketDepthService.fetchMarketDepth()-");
	}

	public void fetchMarketDepthByInstrument() throws Exception {
		log.info("MarketDepthService.fetchMarketDepthAByInstrument()+");
		Iterable<MarketDepth> marketDepthByInst = marketDepthRepository.findAllByInstId(BigInteger.valueOf(1));
		for (MarketDepth mkd : marketDepthByInst) {
			log.info("ID:" + mkd.getId());
			log.info("INST_ID:" + mkd.getInstId());
			log.info("CURRENCY:" + mkd.getCurrency());
			log.info("CUSIP:" + mkd.getCusip());
			log.info("AsOf:" + mkd.getAsOf());
		}
		log.info("MarketDepthService.fetchMarketDepthAByInstrument()-");
	}

	public void updateMarketDepth() throws Exception {
		log.info("MarketDepthService.updateMarketDepth()+");
		Iterable<MarketDepth> marketDepthByInst = marketDepthRepository.findAllByCurrency("USD");
		for (MarketDepth mkd : marketDepthByInst) {
			log.info("ID:" + mkd.getId());
			log.info("INST_ID:" + mkd.getInstId());
			log.info("CURRENCY:" + mkd.getCurrency());
			log.info("CUSIP:" + mkd.getCusip());
			log.info("AsOf:" + mkd.getAsOf());
			mkd.setCurrency("EUR");
		}
		log.info("MarketDepthService.updateMarketDepth()-");
		log.info("Updating Currency.............Started!!");
		marketDepthRepository.saveAll(marketDepthByInst);
		log.info("Updating Currency.............Completed!!");
	}

	public void deleteMarketDepth(){
		Iterable<MarketDepth> marketDepthByInst = marketDepthRepository.findAllByCurrency("EUR");
		for(MarketDepth mkd:marketDepthByInst){
			log.info("Deleting Currency.............started!!");
			marketDepthRepository.delete(mkd);
			log.info("Deleting Currency.............Completed!!");
		}
	}
}
