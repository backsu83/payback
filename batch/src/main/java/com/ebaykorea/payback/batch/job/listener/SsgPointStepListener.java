package com.ebaykorea.payback.batch.job.listener;

import com.ebaykorea.payback.batch.client.teams.TeamsAlarmClient;
import com.ebaykorea.payback.batch.client.teams.dto.TeamsMessageDto;
import com.ebaykorea.payback.batch.config.properties.ApiInfoProperties;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.metrics.BatchMetrics;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class SsgPointStepListener implements StepExecutionListener {

  private final ApiInfoProperties apiInfoProperties;
  private final TeamsAlarmClient teamsAlarmClient;

  @Override
  public void beforeStep(final StepExecution stepExecution) {
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
            "[%s]\n\r[%s:%d]\n\r",
            apiInfoProperties.getName(),
            stepExecution.getStepName(),
            stepExecution.getJobExecutionId()
    );
    stepExecution.setEndTime(new Date());
    Duration stepExecutionDuration = BatchMetrics.calculateDuration(stepExecution.getStartTime(), stepExecution.getEndTime());

    var message = PREFIX + String.format("[readCount:%d]\n\r"
            + "[writeCount:%d]\n\r"
            + "[skipCount:%d]\n\r"
            + "[duration:%s]",
        stepExecution.getReadCount(),
        stepExecution.getWriteCount(),
        stepExecution.getSkipCount(),
        BatchMetrics.formatDuration(stepExecutionDuration)
    );

    log.info(message);
    teamsAlarmClient.sendSync(TeamsMessageDto.builder()
            .title("[SSGPOINT_BATCH_JOB]")
            .text(message)
            .build());

    List<Throwable> exceptions = stepExecution.getFailureExceptions();
    if (CollectionUtils.isEmpty(exceptions)) {
      return ExitStatus.COMPLETED;
    } else {
      exceptions.forEach(e -> {
            log.error(e.getMessage(), e);
          }
      );
      return ExitStatus.FAILED;
    }
  }
}
