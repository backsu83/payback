package com.ebaykorea.payback.config.persistence.jpa;

import com.ebaykorea.payback.config.properties.HibernateProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static com.ebaykorea.payback.config.persistence.jpa.GmarketStardbConfig.PACKAGE_NAME;


@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Configuration
@EnableJpaRepositories(
        basePackages = PACKAGE_NAME,
        entityManagerFactoryRef = "gmarketStardbJpaEntityManagerFactory",
        transactionManagerRef = "gmarketStardbJpaTransactionManager"
)
public class GmarketStardbConfig extends HibernateAdapterFactory {

    private final HibernateProperties hibernateProperties;
    public final static String PACKAGE_NAME = "com.ebaykorea.payback.core.persistence.mssql.stardb.reward";

    public GmarketStardbConfig(HibernateProperties hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean gmarketStardbJpaEntityManagerFactory(@Qualifier("gmarketStardbDataSource") DataSource ds) {
        LocalContainerEntityManagerFactoryBean factoryBean = getJpaEntityManagerFactory(ds,
                PACKAGE_NAME);
        factoryBean.setJpaProperties(hibernateProperties.getInfo());
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

    @Bean
    @Primary
    public PlatformTransactionManager gmarketStardbJpaTransactionManager(@Qualifier("gmarketStardbDataSource") DataSource ds) {
        //noinspection ConstantConditions
        return new JpaTransactionManager(gmarketStardbJpaEntityManagerFactory(ds).getObject());
    }

}
