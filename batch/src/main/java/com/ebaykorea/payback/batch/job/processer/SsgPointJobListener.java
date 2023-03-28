package com.ebaykorea.payback.batch.job.processer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SsgPointJobListener implements JobExecutionListener {

  @Override
  public void beforeJob(final JobExecution jobExecution) {
    jobExecution.setExitStatus(new ExitStatus("FAILED","JOB CUSTOM EXCEPTION MESSAGE"));
  }

  @Override
  public void afterJob(final JobExecution jobExecution) {

  }
}
