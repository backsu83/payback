package com.ebaykorea.payback.batch.job.processer;

import com.ebaykorea.payback.batch.config.properties.ApiInfoProperties;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class SsgPointStepListener implements StepExecutionListener {

  private final ApiInfoProperties apiInfoProperties;

  @Override
  public void beforeStep(final StepExecution stepExecution) {
    stepExecution.setExitStatus(new ExitStatus("FAILED","STEP CUSTOM EXCEPTION MESSAGE"));
    log.info(
        "[{}][{}][{}] JobExecution beforeStep",
        apiInfoProperties.getName(),
        stepExecution.getStepName(),
        stepExecution.getId()
    );
  }

  @Override
  public ExitStatus afterStep(final StepExecution stepExecution) {

    String PREFIX = String.format(
        "[%s][%s][%d]",
        apiInfoProperties.getName(),
        stepExecution.getStepName(),
        stepExecution.getJobExecutionId()
    );

    log.info(
        "{} JobExecution afterStep [readCount:{}][writeCount:{}][errorSize:{}]",
        PREFIX,
        stepExecution.getReadCount(),
        stepExecution.getWriteCount(),
        stepExecution.getFailureExceptions().size()
    );

    List<Throwable> exceptions = stepExecution.getFailureExceptions();
    if (CollectionUtils.isEmpty(exceptions)) {
      log.info("{} SUCCESS [readCount:{}][writeCount:{}]",
          PREFIX,
          stepExecution.getReadCount(),
          stepExecution.getWriteCount()
      );

      return ExitStatus.COMPLETED;
    } else {
      exceptions.forEach(e -> {
            stepExecution.setExitStatus(new ExitStatus("FAILED","STEP CUSTOM EXCEPTION MESSAGE"));
            log.error(e.getMessage(), e);
          }
      );

      return ExitStatus.FAILED;
    }
  }
}
