package com.ebaykorea.payback.scheduler;

import com.ebaykorea.saturn.datasource.EnableSaturnDataSource;
import com.ebaykorea.saturn.moa.EnableMoA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableMoA
@EnableFeignClients
@EnableSaturnDataSource
@EnableScheduling
@SpringBootApplication
public class SchedulerApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(SchedulerApplication.class, args);
  }

  @Override
  public void run(final String... args) {
    log.info("payback-scheduler application is running..");
  }

}
