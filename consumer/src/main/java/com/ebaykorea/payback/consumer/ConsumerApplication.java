package com.ebaykorea.payback.consumer;

import com.ebaykorea.payback.consumer.config.properties.ApiInfoProperties;
import com.ebaykorea.saturn.datasource.EnableSaturnDataSource;
import com.ebaykorea.saturn.moa.EnableMoA;
import com.ebaykorea.saturn.starter.config.SaturnApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;

@EnableMoA
@EnableFeignClients
@EnableSaturnDataSource
@SpringBootApplication
@EnableConfigurationProperties({
    ApiInfoProperties.class,
    SaturnApplicationProperties.class
})
public class ConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void ready() {
    System.out.println("payback-consumer is ready");
  }
}
