package com.ebaykorea.payback.batch.scheduler;

import com.ebaykorea.payback.batch.job.SsgPointDailyJobConfig;
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
public class SsgPointDailyScheduler {

  private final JobLauncher jobLauncher;
  private final SsgPointDailyJobConfig jobConfig;

  @Scheduled(cron = "${ssgpoint.scheduler.daily.crontab}")
  public void runJob() {
    String now = LocalDateTime.now()
        .format(PaybackDateTimes.DATE_TIME_FORMATTER);
    JobParameters jobParameters = new JobParametersBuilder()
        .addString("dailyTime", now).toJobParameters();

    try {
      log.debug("jobLauncher daily start...");
      jobLauncher.run(jobConfig.ssgpointDailyJob(), jobParameters);
    } catch (JobExecutionException e) {
      System.out.println("e.getMessage() = " + e.getMessage());
      // TODO: add job exeception
    }
  }
}
