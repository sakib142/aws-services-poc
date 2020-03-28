package com.mktx.order.svc.gradle;

import com.mktx.order.svc.gradle.common.config.EMSBeanConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories( {"com.mktx.order.svc.gradle.repository"} )
@EntityScan({"com.mktx.order.svc.gradle.model"})
@Import(EMSBeanConfiguration.class)
public class EmsOrderSvcGradleprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmsOrderSvcGradleprojectApplication.class, args);
	}

}
