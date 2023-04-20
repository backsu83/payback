package com.ebaykorea.payback;

import com.ebaykorea.payback.config.properties.ApiInfoProperties;
import com.ebaykorea.saturn.moa.EnableMoA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@EnableMoA
@EnableFeignClients
@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("payback-api application is running..");
    }
}


