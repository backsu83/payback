package com.ebaykorea.payback.batch.scheduler;

import com.ebaykorea.payback.batch.job.SsgPointTargetJobConfig;
import com.ebaykorea.payback.batch.util.PaybackDateTimes;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SsgPointTargetScheduler {
  private final JobLauncher jobLauncher;
  private final SsgPointTargetJobConfig jobConfig;

  // 운영 09:00~23:30
//  @Scheduled(cron = "10 * * * * *")
//  public void runJob() {
//    String now = LocalDateTime.now()
//        .format(PaybackDateTimes.DATE_TIME_FORMATTER);
//    JobParameters jobParameters = new JobParametersBuilder()
//        .addString("targetTime", now).toJobParameters();

//    try {
//      log.debug("jobLauncher start...");
//      jobLauncher.run(jobConfig.ssgpointTargetJob(), jobParameters);
//    } catch (JobExecutionException e) {
//      System.out.println("e.getMessage() = " + e.getMessage());
//      // TODO: add job exeception
//    }
//  }
}
