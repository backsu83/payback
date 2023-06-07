package com.ebaykorea.payback.batch.scheduler;

import com.ebaykorea.payback.batch.job.SsgPointTargetJobConfig;
import com.ebaykorea.payback.batch.util.PaybackDateTimes;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

  @Scheduled(cron = "${ssgpoint.scheduler.target.crontab}")
  public void runJob() {
    LocalDateTime currentTime = LocalDateTime.now();
    LocalDateTime startDateTime = currentTime.with(LocalTime.of(23, 30));
    LocalDateTime endDateTime = startDateTime.plusMinutes(31);
    if (currentTime.isAfter(startDateTime) && currentTime.isBefore(endDateTime)) {
      log.info("jobLauncher fail to start : {}", currentTime);
      return;
    }

    JobParameters jobParameters = new JobParametersBuilder()
            .addString("targetTime", currentTime.format(PaybackDateTimes.DATE_TIME_FORMATTER)).toJobParameters();

    try {
      log.debug("jobLauncher start...");
      jobLauncher.run(jobConfig.ssgpointTargetJob(), jobParameters);
    } catch (JobExecutionException e) {
      System.out.println("e.getMessage() = " + e.getMessage());
      // TODO: add job exeception
    }
  }
}
