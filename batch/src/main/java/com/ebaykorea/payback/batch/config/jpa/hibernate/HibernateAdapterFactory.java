package com.ebaykorea.payback.batch.config.jpa.hibernate;

import com.ebaykorea.payback.batch.config.properties.HibernateProperties;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

public class HibernateAdapterFactory {

  @Autowired
  protected HibernateProperties hibernateProperties;

  /**
   * 기본 설정입니다. 별도의 설정이 필요하면 Override 하면 됩니다.
   *
   * @return Default HibernateJpaVendorAdapter
   */
  protected HibernateJpaVendorAdapter getJpaVendorAdapter() {
    HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    jpaVendorAdapter.setShowSql(hibernateProperties.getShowSql());
    jpaVendorAdapter.setGenerateDdl(false);

    return jpaVendorAdapter;
  }

  /**
   * 설정을 포함한 entity manager factory 를 만듭니다.
   *
   * @param ds          DataSource
   * @param packageName 패키지명
   * @return entity manager factory
   */
  protected LocalContainerEntityManagerFactoryBean getJpaEntityManagerFactory(
      DataSource ds,
      String packageName
  ) {
    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setDataSource(ds);
    factoryBean.setPackagesToScan(packageName);
    factoryBean.setJpaVendorAdapter(getJpaVendorAdapter());

    Map<String, Object> props = new HashMap<>();
    props.put("hibernate.physical_naming_strategy", hibernateProperties.getNamingPhysicalStrategy());
    factoryBean.setJpaPropertyMap(props);

    return factoryBean;
  }
}