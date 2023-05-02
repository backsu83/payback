package com.ebaykorea.payback.batch.job.listener;

import com.ebaykorea.payback.batch.client.teams.TeamsAlarmClient;
import com.ebaykorea.payback.batch.client.teams.dto.TeamsMessageDto;
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
        "[%s]\n[%s:%d]\n",
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
      log.info("{} sucess [readCount:{}][writeCount:{}]",
          PREFIX,
          stepExecution.getReadCount(),
          stepExecution.getWriteCount()
      );
      long timeDifferenceMillis = stepExecution.getEndTime().getTime() - stepExecution.getStartTime().getTime();
      long timeDifferenceMinutes = timeDifferenceMillis / (60 * 1000);
      var message = PREFIX + String.format("[readCount:%d]\n[writeCount:%d]\n[duration:%d]",
          stepExecution.getReadCount(),
          stepExecution.getWriteCount(),
          timeDifferenceMinutes
      );

      teamsAlarmClient.send(TeamsMessageDto.builder()
          .text(message)
          .build());

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
