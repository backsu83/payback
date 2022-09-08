package com.ebaykorea.payback.core.config.persistence.jpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
@EnableJpaRepositories(
        basePackages = GmarketTigerdbConfig.PACKAGE_NAME,
        entityManagerFactoryRef = "gmarketTigerJpaEntityManagerFactory",
        transactionManagerRef = "gmarketTigerJpaTransactionManager"
)
public class GmarketTigerdbConfig extends HibernateAdapterFactory {

    public final static String PACKAGE_NAME = "com.ebaykorea.payback.core.persistence.mssql.tiger";

    @Bean
    public LocalContainerEntityManagerFactoryBean gmarketTigerJpaEntityManagerFactory(@Qualifier("tigerDataSource") DataSource ds) {
        return getJpaEntityManagerFactory(ds, PACKAGE_NAME);
    }

    @Bean
    public PlatformTransactionManager gmarketTigerJpaTransactionManager(@Qualifier("tigerDataSource") DataSource ds) {
        //noinspection ConstantConditions
        return new JpaTransactionManager(gmarketTigerJpaEntityManagerFactory(ds).getObject());
    }
}
