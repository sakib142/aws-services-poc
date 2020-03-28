package com.trax.ems.security.route.processor;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.trax.ems.security.constants.EmsMessageProcessConstant;
import com.trax.ems.security.service.SecurityFeedService;
import com.trax.ems.security.utils.EmsBatchProcessorUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityRestProcessor implements Processor {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private SecurityFeedService securityFeedService;
	@Autowired
	private EmsBatchProcessorUtil emsBatchProcessorUtil;

	@Value("${batch.name}")
	private String batchName;

	@Value("${batch.batchsize}")
	private Integer stdBatchSize;

	@Value("${batch.timestamp.format}")
	private String TIMESTAMP_FORMAT;

	@Value("${endpoint.url}")
	private String restEndPoint;

	@Override
	public void process(Exchange exchange) throws Exception {
		log.info("SecurityRestProcessor.process()+");

		Instant lastbatchEndTsmtp = securityFeedService.getLastExecutionTime(batchName);
		SimpleDateFormat dateformat = new SimpleDateFormat(TIMESTAMP_FORMAT);
		Date lasbatchEndDate = Date.from(lastbatchEndTsmtp);
		if (emsBatchProcessorUtil.getIsCurrentRunInprogress() == null) {

			emsBatchProcessorUtil.setBatchStartTimestamp(dateformat.format(lasbatchEndDate));
			emsBatchProcessorUtil.setBatchEndTimestamp(dateformat.format(new Date()));
			emsBatchProcessorUtil.setNoOfSecuritiesProcessed(BigInteger.ZERO);
			emsBatchProcessorUtil.setBatchName(batchName);
			emsBatchProcessorUtil.setBatchSize(stdBatchSize);
			emsBatchProcessorUtil.setIsCurrentRunInprogress(true);
		} else {
			if (!emsBatchProcessorUtil.getIsCurrentRunInprogress()) {
				emsBatchProcessorUtil.setBatchStartTimestamp(dateformat.format(lasbatchEndDate));
				emsBatchProcessorUtil.setBatchEndTimestamp(dateformat.format(new Date()));
			}
		}

		Integer page = (Integer) exchange.getIn().getHeader(EmsMessageProcessConstant.PAGE_NUMBER.name());
		emsBatchProcessorUtil.setPage(page == null ? -1 : page);
		if (emsBatchProcessorUtil.getBatchSize() == null || emsBatchProcessorUtil.getBatchSize() == 0)
			emsBatchProcessorUtil.setBatchSize(500);

		String response = getRestResponse(emsBatchProcessorUtil);
		setResponseHeader(exchange, emsBatchProcessorUtil, response);
		setResponseBody(exchange, response);
		log.info("SecurityRestProcessor.process()-");
	}

	private String getRestResponse(EmsBatchProcessorUtil emsBatchProcessorUtil) {
		log.info("SecurityRestProcessor.getRestResponse()+");
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(EmsMessageProcessConstant.PAGE_NUMBER.name(), 1 + emsBatchProcessorUtil.getPage());
		body.put(EmsMessageProcessConstant.BATCH_SIZE.name(), emsBatchProcessorUtil.getBatchSize());
		emsBatchProcessorUtil.setPage(emsBatchProcessorUtil.getPage() + 1);
		String REST_ENDPOINT_URL = restEndPoint + emsBatchProcessorUtil.getBatchStartTimestamp() + ","
				+ emsBatchProcessorUtil.getBatchEndTimestamp() + "," + emsBatchProcessorUtil.getPage() + ","
				+ emsBatchProcessorUtil.getBatchSize();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
		ResponseEntity<String> countResponseEntity = restTemplate().exchange(REST_ENDPOINT_URL, HttpMethod.GET, entity,
				String.class);
		log.info("SecurityRestProcessor.getRestResponse()-");
		return countResponseEntity.getBody();
	}

	private void setResponseBody(Exchange exchange, String response) {
		log.info("SecurityRestProcessor.setResponseBody()+");
		exchange.getOut().setHeaders(exchange.getIn().getHeaders());
		exchange.getOut().setBody(response);
		log.info("SecurityRestProcessor.setResponseBody()-");
	}

	private void setResponseHeader(Exchange exchange, EmsBatchProcessorUtil emsBatchProcessorUtil, String response) {

		log.info("SecurityRestProcessor.setResponseHeader()+");

		if (response.isEmpty() || response == "" || response.equals("[]"))
			exchange.getIn().setHeader(EmsMessageProcessConstant.BATCH_EXEC_COMPLETION_FLAG.name(), true);
		else
			exchange.getIn().setHeader(EmsMessageProcessConstant.BATCH_EXEC_COMPLETION_FLAG.name(), false);
		exchange.getIn().setHeader(EmsMessageProcessConstant.BATCH_NAME.name(), emsBatchProcessorUtil.getBatchName());
		exchange.getIn().setHeader(EmsMessageProcessConstant.PAGE_NUMBER.name(), emsBatchProcessorUtil.getPage());
		exchange.getIn().setHeader(EmsMessageProcessConstant.BATCH_SIZE.name(), emsBatchProcessorUtil.getBatchSize());
		exchange.getIn().setHeader(EmsMessageProcessConstant.BATCH_START_TIME.name(),
				emsBatchProcessorUtil.getBatchStartTimestamp());
		exchange.getIn().setHeader(EmsMessageProcessConstant.BATCH_END_TIME.name(),
				emsBatchProcessorUtil.getBatchEndTimestamp());
		@SuppressWarnings("unchecked")
		List<Object> emsList = (List<Object>) exchange.getIn().getBody();
		long size = emsList != null ? emsList.size() : 0;
		emsBatchProcessorUtil.setNoOfSecuritiesProcessed(
				emsBatchProcessorUtil.getNoOfSecuritiesProcessed().add(BigInteger.valueOf(size)));
		exchange.getIn().setHeader(EmsMessageProcessConstant.NO_OF_RECORD_PROCESSED.name(),
				emsBatchProcessorUtil.getNoOfSecuritiesProcessed());
		log.info("noOfRecordProcessed: " + emsBatchProcessorUtil.getNoOfSecuritiesProcessed());
		log.info("SecurityRestProcessor.setResponseHeader()-");
	}

	public void resetEmsBatchExecution() {
		log.info("SecurityRestProcessor.getRestResponse()+");
		emsBatchProcessorUtil.setIsCurrentRunInprogress(false);
		log.info("SecurityRestProcessor.getRestResponse()+");
	}
}
