package com.trax.ems.security.route;

import java.math.BigInteger;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.trax.ems.security.constants.EmsMessageProcessConstant;
import com.trax.ems.security.datasource.ems.domain.EmsInstrument;
import com.trax.ems.security.route.processor.SecurityRestProcessor;
import com.trax.ems.security.service.SecurityFeedService;

@Component
public class SecurityFeedServiceRouteBuilder extends SpringRouteBuilder {

	@Autowired
	private SecurityFeedService securityFeedService;

	@Autowired
	private SecurityRestProcessor SecurityRestProcessor;

	@Value("${batch.frequency}")
	private Integer BATCH_FREQUENCY;

	@Value("${batch.scheduler}")
	private String BATCH_SCHEDULER;

	public void configure() {
		log.info("SecurityFeedServiceRouteBuilder.configure()+");
		addRestletConfig();
		processJson();
		log.info("SecurityFeedServiceRouteBuilder.configure()-");
	}

	private void addRestletConfig() {
		log.info("SecurityFeedServiceRouteBuilder.addRestletConfig()+");
		/**
		 * Used 'restlet', which is a component for providing REST services.
		 */
		restConfiguration().component("restlet").host("0.0.0.0").port("{{camelrestlet.server.port}}")
				.bindingMode(RestBindingMode.auto);
		log.info("SecurityFeedServiceRouteBuilder.addRestletConfig()-");
	}

	protected void processJson() {
		log.info("SecurityFeedServiceRouteBuilder.processJson()+");
		String scheduler = BATCH_SCHEDULER.concat(BATCH_FREQUENCY.toString());
		from(scheduler).loopDoWhile(stopLoopPredicate()).to("bean:securityRestProcessor").unmarshal(
				new ListJacksonDataFormat(new ObjectMapper().registerModule(new JavaTimeModule()), EmsInstrument.class))
				.bean(securityFeedService, "feedSecurityDetails").end();

		log.info("SecurityFeedServiceRouteBuilder.processJson()-");
	}

	public Predicate stopLoopPredicate() {
		log.info("SecurityFeedServiceRouteBuilder.stopLoopPredicate()+");
		Predicate stopLoop = new Predicate() {
			@Override
			public boolean matches(Exchange exchange) {

				if (exchange.getIn().getBody() == null
						&& exchange.getIn().getHeader(EmsMessageProcessConstant.PAGE_NUMBER.name()) == null) {
					return true;
				} else {

					if (exchange.getIn().getHeader(EmsMessageProcessConstant.BATCH_EXEC_COMPLETION_FLAG.name()) != null
							&& exchange.getIn().getHeader(EmsMessageProcessConstant.BATCH_EXEC_COMPLETION_FLAG.name())
									.equals(true)) {
						String batchStartTimestamp = exchange.getIn()
								.getHeader(EmsMessageProcessConstant.BATCH_START_TIME.name()).toString();
						String batchEndTimestamp = exchange.getIn()
								.getHeader(EmsMessageProcessConstant.BATCH_END_TIME.name()).toString();
						Long noOfRecordProcessed = Long.parseLong(exchange.getIn()
								.getHeader(EmsMessageProcessConstant.NO_OF_RECORD_PROCESSED.name()).toString());
						securityFeedService.updateBatchExecutionDetails(batchStartTimestamp, batchEndTimestamp,
								BigInteger.valueOf(noOfRecordProcessed));
						SecurityRestProcessor.resetEmsBatchExecution();
						return false;
					}
					return true;
				}

			}
		};
		log.info("SecurityFeedServiceRouteBuilder.stopLoopPredicate()-");
		return stopLoop;
	}
}
