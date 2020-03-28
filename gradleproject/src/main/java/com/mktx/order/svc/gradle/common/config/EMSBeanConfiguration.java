package com.mktx.order.svc.gradle.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mktx.order.svc.gradle.common.constants.EMSConstants;
import com.mktx.order.svc.gradle.model.OrderDTO;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <strong>Bean configuration/definition class</strong>
 * 
 * <p>
 * Beans configured in this class will be available in the runtime if it is imported shown in example below. Methods
 * annotated with {@link Bean} returns a bean managed by the Spring container, by default method name is the bean
 * reference which can be used.
 * </p>
 * 
 * <pre class="code">
 * &#064;SpringBootApplication
 * &#064;Import(EMSBeanConfiguration.class)
 * public class MainClass {
 * 
 *     public static void main(String[] args) {
 *         SpringApplication.run(MainClass.class, args);
 *     }
 *
 * }</pre>
 * 
 * @author rguduri
 *
 */
@Configuration
public class EMSBeanConfiguration {



    /**
     * @return ObjectMapper
     */
    private ObjectMapper objectMapper() {
        /**
         * 
         * Registering JavaTimeModule to support for Java 8 date/time types 
         * while using Jackson to marshal to and from JSON.
         * 
         */
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    /**
     * @return {@link JacksonDataFormat} with the custom mapper
     * registered with the {@link JavaTimeModule} support.
     * 
     * @see <a href="https://github.com/FasterXML/jackson-modules-java8">
     * https://github.com/FasterXML/jackson-modules-java8</a>
     */
    @Bean(name = EMSConstants.ORDERDTO_JSONDATAFORMAT)
    public DataFormat orderDTOJacksonDataFormat() {

        ObjectMapper objectMapper = objectMapper();
        return new JacksonDataFormat(objectMapper, OrderDTO.class);
    }

}
