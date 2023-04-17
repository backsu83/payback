package com.ebaykorea.payback.batch.job.tasklet;


import com.ebaykorea.payback.batch.client.ssgpoint.SsgPointApiClient;
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SsgPointDailyTasklet implements Tasklet, StepExecutionListener {

  private final SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;
  private final SsgPointApiClient ssgPointApiClient;

  @Override
  public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext)
      throws Exception {
    //TODO 신세계 포인트 Daily 데이터 저장 서비스 로직 구현



    //종료
    return RepeatStatus.FINISHED;
  }


  @Override
  public void beforeStep(final StepExecution stepExecution) {

  }

  @Override
  public ExitStatus afterStep(final StepExecution stepExecution) {
    return null;
  }
}
