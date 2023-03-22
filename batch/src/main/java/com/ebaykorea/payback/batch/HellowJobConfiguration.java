package com.ebaykorea.payback.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HellowJobConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job hellowJob() {
    return jobBuilderFactory.get("hellowJob")
        .start(hellowStep())
        .build();
  }

  @Bean
  public Step hellowStep() {
    return stepBuilderFactory.get("hellowStep")
        .tasklet((contribution, chunkContext) ->{
          System.out.println("hellow step");
          return RepeatStatus.FINISHED;
        })
        .build();
  }

}
