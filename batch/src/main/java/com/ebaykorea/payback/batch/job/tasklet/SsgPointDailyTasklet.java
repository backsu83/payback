package com.ebaykorea.payback.batch.job.tasklet;


import com.ebaykorea.payback.batch.config.properties.SsgPointAuthProperties;
import com.ebaykorea.payback.batch.domain.SsgPointCertifier;
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.VerifyTradeType;
import com.ebaykorea.payback.batch.service.SsgPointBatchService;
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

  private final SsgPointAuthProperties authProperties;
  private final SsgPointBatchService ssgPointBatchService;

  @Override
  public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext)
      throws Exception {
    //token 발급 - 지마켓
    final var gmktAuthInfo = SsgPointCertifier.of(authProperties, OrderSiteType.Gmarket);

    //일대사 api 호출 - 적립
    final var gmktSaveResult = ssgPointBatchService.verify(gmktAuthInfo, OrderSiteType.Gmarket, VerifyTradeType.Save);
    //일대사 api 호출 - 취소
    final var gmktCancelResult = ssgPointBatchService.verify(gmktAuthInfo, OrderSiteType.Gmarket, VerifyTradeType.Cancel);
    //token 발급 - 옥션
    final var iacAuthInfo = SsgPointCertifier.of(authProperties, OrderSiteType.Auction);
    //일대사 api 호출 - 적립
    final var iacSaveResult = ssgPointBatchService.verify(iacAuthInfo, OrderSiteType.Auction, VerifyTradeType.Save);
    //일대사 api 호출 - 취소
    final var iacCancelResult = ssgPointBatchService.verify(iacAuthInfo, OrderSiteType.Auction, VerifyTradeType.Cancel);


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
