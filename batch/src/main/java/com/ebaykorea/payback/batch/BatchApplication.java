package com.ebaykorea.payback.batch;

import com.ebaykorea.payback.batch.config.properties.ApiInfoProperties;
import com.ebaykorea.saturn.datasource.EnableSaturnDataSource;
import com.ebaykorea.saturn.moa.EnableMoA;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableMoA
@EnableSaturnDataSource
//@EnableBatchProcessing
@SpringBootApplication
@EnableConfigurationProperties({
    ApiInfoProperties.class
})
public class BatchApplication {

  public static void main(String[] args) {
    SpringApplication.run(BatchApplication.class, args);
  }

}
