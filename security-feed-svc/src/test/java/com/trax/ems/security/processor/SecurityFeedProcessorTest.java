package com.trax.ems.security.processor;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import com.trax.ems.security.datasource.ems.repo.EmsBatchExecutionRespository;
import com.trax.ems.security.route.processor.SecurityRestProcessor;
import com.trax.ems.security.service.SecurityFeedService;
import com.trax.ems.security.utils.EmsBatchProcessorUtil;

//@RunWith(MockitoJUnitRunner.class)
public class SecurityFeedProcessorTest {

	@InjectMocks
	SecurityRestProcessor securityRestProcessor;

	@Mock
	SecurityFeedService securityFeedService;

	@Mock
	EmsBatchExecutionRespository emsBatchExecutionRespository;

	@Mock
	private EmsBatchProcessorUtil emsBatchProcessorUtil;

	@Mock
	private Exchange exchange;

	CamelContext camelContext;

	@Value("${batch.name}")
	private String batchName;

	@Value("${batch.batchsize}")
	private Integer stdBatchSize;

	@Value("${batch.timestamp.format}")
	private String TIMESTAMP_FORMAT;

	@Value("${endpoint.url}")
	private String restEndPoint;

	//private static final String BATCHNAME = "ems.securityfeed";
	// private static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmssSS";

	@Before
	public void setup() throws ParseException {
		MockitoAnnotations.initMocks(this);
		camelContext = new DefaultCamelContext();
		exchange = new DefaultExchange(camelContext);
		ReflectionTestUtils.setField(securityRestProcessor, "TIMESTAMP_FORMAT", "yyyyMMddHHmmssSS");
		ReflectionTestUtils.setField(securityRestProcessor, "stdBatchSize", 1000);
		ReflectionTestUtils.setField(securityRestProcessor, "batchName", "ems.securityfeed");
		ReflectionTestUtils.setField(securityRestProcessor, "restEndPoint", "http://localhost:6031/security/");

	}

	//@Test
	public void testProcess() throws Exception {
		Instant value = Instant.now();
		//securityRestProcessor.process(exchange);
		when(securityFeedService.getLastExecutionTime(any())).thenReturn(value);
		when(emsBatchProcessorUtil.getIsCurrentRunInprogress()).thenReturn(null);
		//when(emsBatchProcessorUtil.getBatchName())
				//.thenReturn(ReflectionTestUtils.getField(securityRestProcessor, "BATCHNAME").toString());


		Date lasbatchEndDate = Date.from(value);
		//when(emsBatchProcessorUtil.getBatchStartTimestamp()).thenReturn(dateformat.format(lasbatchEndDate));
		when(emsBatchProcessorUtil.getIsCurrentRunInprogress()).thenReturn(true);
		securityRestProcessor.process(exchange);
		verify(securityFeedService,times(1)).getLastExecutionTime(any());
       // verify(emsBatchProcessorUtil,times(1)).getIsCurrentRunInprogress();
        //verify(securityRestProcessor,times(1)).g


		//when(emsBatchProcessorUtil.getBatchEndTimestamp()).thenReturn(dateformat.format(lasbatchEndDate));
		//verify(emsBatchExecutionRespository, times(1)).findById(any());
	}




}
