package com.ebaykorea.payback.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadExecutorConfig {

  @Bean
  public TaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor(); // (2)
    executor.setCorePoolSize(5);
    executor.setMaxPoolSize(5);
    executor.setQueueCapacity(10);
    executor.setKeepAliveSeconds(0);      // QueueCapacity 초과한 max 스레드 유지 시간
    executor.setThreadNamePrefix("batch-thread-");
    executor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
    executor.initialize();
    return executor;
  }

}
