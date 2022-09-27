package com.ebaykorea.payback.adapter.persistence;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class
})
@ComponentScan(basePackages = {
    "com.ebaykorea.payback.config",
    "com.ebaykorea.payback.adapter.persistence",
    "com.ebaykorea.payback.port.persistence",
})
public class DatabaseConfigTest {

}
