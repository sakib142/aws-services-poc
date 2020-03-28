package com.trax.ems.security.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "blinkEntityManagerFactory",
        transactionManagerRef = "blinkTransactionManager",
        basePackages = {
                "com.trax.ems.security.datasource.blink.repo"})
public class DatastoreConfigBlink {

    @Bean(name = "blinkDataSource")
    @ConfigurationProperties(prefix = "blink.datasource")
    public DataSource dataSource() throws SQLException {

        return DataSourceBuilder.create().build();
    }

    @Bean(name = "blinkEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean blinkEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                            @Qualifier("blinkDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean eb = builder.dataSource(dataSource)
                .packages("com.trax.ems.security.datasource.blink.domain").persistenceUnit("blink").build();
        eb.setValidationMode(ValidationMode.NONE);
        return eb;
    }

    @Bean(name = "blinkTransactionManager")
    public PlatformTransactionManager blinkTransactionManager(
            @Qualifier("blinkEntityManagerFactory") EntityManagerFactory blinkEntityManagerFactory) {
        return new JpaTransactionManager(blinkEntityManagerFactory);
    }
}
