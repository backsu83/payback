package com.ebaykorea.payback.schedulercluster;

import com.ebaykorea.saturn.datasource.EnableSaturnDataSource;
import com.ebaykorea.saturn.moa.EnableMoA;
import com.ebaykorea.saturn.starter.config.SaturnApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableMoA
@EnableFeignClients
@EnableSaturnDataSource
@EnableScheduling
@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties({
    SaturnApplicationProperties.class
})
public class SchedulerClusterApplication {

  public static void main(String[] args) {
    SpringApplication.run(SchedulerClusterApplication.class, args);
  }

}
