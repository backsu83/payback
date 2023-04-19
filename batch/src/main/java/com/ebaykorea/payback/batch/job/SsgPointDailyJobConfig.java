package com.ebaykorea.payback.batch.job;

import com.ebaykorea.payback.batch.job.tasklet.SsgPointDailyTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SsgPointDailyJobConfig {

  public static final String JOB_NAME = "ssgpointDailyJob";
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final SsgPointDailyTasklet ssgPointDailyTasklet;

  @Bean
  public Job ssgpointDailyJob() {
    return jobBuilderFactory.get(JOB_NAME)
        .start(ssgpointDailyStep())
        .build();
  }

  @Bean
  public Step ssgpointDailyStep() {
    return stepBuilderFactory.get(JOB_NAME)
        .tasklet(ssgPointDailyTasklet)
        .build();
  }


}
