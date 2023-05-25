package com.ebaykorea.payback.batch.config.jpa;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JpaBatchConfigurer;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfiguration extends JpaBatchConfigurer {

  private final DataSource dataSource;
  private final String tablePrefix;

  /**
   * Create a new {@link JobConfiguration} instance.
   *
   * @param properties                    the batch properties
   * @param dataSource                    the underlying data source
   * @param transactionManagerCustomizers transaction manager customizers (or {@code null})
   * @param entityManagerFactory          the entity manager factory (or {@code null})
   */
  protected JobConfiguration(BatchProperties properties, DataSource dataSource,
      TransactionManagerCustomizers transactionManagerCustomizers,
      EntityManagerFactory entityManagerFactory) {
    super(properties, dataSource, transactionManagerCustomizers, entityManagerFactory);
    this.tablePrefix = properties.getTablePrefix();
    this.dataSource = dataSource;
  }

  @Override
  protected JobRepository createJobRepository() throws Exception {

    JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
    factory.setDataSource(dataSource);
    factory.setTransactionManager(getTransactionManager());
    factory.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
    factory.setTablePrefix(tablePrefix);
    return factory.getObject();
  }
}
