package com.ebaykorea.payback.infrastructure.gateway;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@EnableFeignClients(basePackages = "com.ebaykorea.payback")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class ApiConfigTest {
}
