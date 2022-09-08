package com.ebaykorea.payback.api;

import com.ebaykorea.saturn.moa.EnableMoA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableMoA
@EnableConfigurationProperties
@EnableFeignClients(basePackages = "com.ebaykorea.payback.core")
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
},
        scanBasePackages = {
        "com.ebaykorea.payback.core",
        "com.ebaykorea.payback.api",
})
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}


