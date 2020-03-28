package com.trax.datadog.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	private static Logger LOGGER = LoggerFactory.getLogger(Controller.class);

	@RequestMapping("/publish")
	public String post() {

		LOGGER.trace("A TRACE Message");
		LOGGER.debug("A DEBUG Message");
		LOGGER.info("An INFO Message");
		LOGGER.warn("A WARN Message");
		LOGGER.error("An ERROR Message");
		 
		Object obj=null;
		System.out.print(obj.toString());
		
		if(true)
			throw new ArithmeticException();
		
		
		return "Published successfully";
	}

}
