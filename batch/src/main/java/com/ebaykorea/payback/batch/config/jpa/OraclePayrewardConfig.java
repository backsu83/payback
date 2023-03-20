package com.ebaykorea.payback.batch.config.jpa;

import com.ebaykorea.payback.batch.config.jpa.hibernate.HibernateAdapterFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = OraclePayrewardConfig.REPOSITORY_PACKAGE_NAME,
    entityManagerFactoryRef = "opayrewrdJpaEntityManagerFactory",
    transactionManagerRef = "opayrewardJpaTransactionManager"
)
@RequiredArgsConstructor
public class OraclePayrewardConfig extends HibernateAdapterFactory {

  public final static String ENTITY_PACKAGE_NAME = "com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity";
  public final static String REPOSITORY_PACKAGE_NAME = "com.ebaykorea.payback.infrastructure.persistence.repository.opayreward";

  @Bean
  public LocalContainerEntityManagerFactoryBean opayrewrdJpaEntityManagerFactory(@Qualifier("o_payrewardDataSource") DataSource datasource) {
    return getJpaEntityManagerFactory(datasource, ENTITY_PACKAGE_NAME);
  }

  @Bean
  public PlatformTransactionManager opayrewardJpaTransactionManager(@Qualifier("o_payrewardDataSource") DataSource datasource) {
    return new JpaTransactionManager(opayrewrdJpaEntityManagerFactory(datasource).getObject());
  }
}