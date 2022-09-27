package com.ebaykorea.payback.config.persistence.jpa;

import com.ebaykorea.payback.config.properties.HibernateProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

/**
 * Jpa Entity Manager 생성 공통 사항
 *
 * {@link #getJpaVendorAdapter()}
 * 설정 값을 불러옵니다. (기본 설정, 필요 시 Override 하여 설정 변경)
 *
 * {@link #getJpaEntityManagerFactory(DataSource, String)}
 * entity manager factory 를 {@link #getJpaVendorAdapter()} 설정에 맞춰 확보합니다.
 */
public class HibernateAdapterFactory {

    @Autowired
    protected HibernateProperties hibernateProperties;

    /**
     * 기본 설정입니다.
     * 별도의 설정이 필요하면 Override 하면 됩니다.
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
     * @param ds DataSource
     * @param packageName 패키지명
     * @return entity manager factory
     */
    protected LocalContainerEntityManagerFactoryBean getJpaEntityManagerFactory(
            DataSource ds,
            String packageName
    ) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        // DCM Starter에서 생성한 DataSource를 EntityManagerFactory에 등록한다.
        factoryBean.setDataSource(ds);
        factoryBean.setPackagesToScan(packageName);
        factoryBean.setJpaVendorAdapter(getJpaVendorAdapter());

        return factoryBean;
    }
}

