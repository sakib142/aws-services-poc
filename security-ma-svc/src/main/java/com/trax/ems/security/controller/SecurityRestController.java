package com.trax.ems.security.controller;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SecurityRestController extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		log.info("SecurityRestController.configure()-");
		addRestletConfig();
		findSecurityDetailsRoute();
		log.info("SecurityRestController.configure()-");
	}

	private void addRestletConfig() {
		log.info("SecurityRestController.addRestletConfig()+");
		/**
		 * Used 'restlet', which is a component for providing REST services.
		 */
		restConfiguration().component("restlet").host("0.0.0.0").port("{{camelrestlet.server.port}}")
				.bindingMode(RestBindingMode.auto);
		log.info("SecurityRestController.addRestletConfig()-");
	}

	public void findSecurityDetailsRoute() {
		log.info("SecurityRestController.findSecurityDetailsRoute()+");

		rest().description("Finding the Instuments by id").path("/security").consumes("application/json")
				.get("{lastTimeStamp},{currentTimeStamp},{page},{batchSize}")
				.to("bean:securityRestService?method=getSecurityDetails(${header.lastTimeStamp},${header.currentTimeStamp},${header.page},${header.batchSize})");

		log.info("SecurityRestController.findSecurityDetailsRoute()-");
	}
}
