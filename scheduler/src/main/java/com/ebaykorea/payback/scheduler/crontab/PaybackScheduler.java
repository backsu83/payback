package com.ebaykorea.payback.scheduler.crontab;

import com.ebaykorea.payback.scheduler.service.PaybackBatchService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.ebaykorea.payback.scheduler.model.constant.TenantCode.GMARKET_TENANT;

@Profile(GMARKET_TENANT)
@Slf4j
@Component
public class PaybackScheduler {

  private final PaybackBatchService paybackSchedulerService;
  private final Long maxTryCount;

  public PaybackScheduler(
      final PaybackBatchService paybackSchedulerService,
      @Value("${com.ebaykorea.payback.scheduler.data.maxTryCount:5}")final Long maxTryCount
  ) {
    this.paybackSchedulerService = paybackSchedulerService;
    this.maxTryCount = maxTryCount;
  }

  @Scheduled(initialDelayString = "${com.ebaykorea.payback.scheduler.data.initialDelay}" , fixedDelayString = "${com.ebaykorea.payback.scheduler.data.fixedDelay}" , timeUnit = TimeUnit.MINUTES)
  public void init() {
    paybackSchedulerService.updateRecords(maxTryCount);
  }

}
