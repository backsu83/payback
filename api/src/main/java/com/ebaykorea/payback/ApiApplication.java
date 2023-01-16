package com.ebaykorea.payback;

import com.ebaykorea.payback.config.properties.ApiInfoProperties;
import com.ebaykorea.saturn.component.core.config.properties.SaturnApplicationProperties;
import com.ebaykorea.saturn.moa.EnableMoA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableMoA
@EnableFeignClients
@SpringBootApplication
@Slf4j
@EnableConfigurationProperties({
    ApiInfoProperties.class,
    SaturnApplicationProperties.class
})
public class ApiApplication implements CommandLineRunner {
    @Autowired
    SaturnApplicationProperties saturnApplicationProperties;

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("payback-api application is running..");
    }
}


