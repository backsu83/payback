package com.ebaykorea.payback.schedulercluster.crontab;

import static com.ebaykorea.payback.schedulercluster.model.constant.TenantCode.GMARKET_TENANT;

import com.ebaykorea.payback.schedulercluster.service.MassSaveAplicationService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile(GMARKET_TENANT)
@Slf4j
@Component
public class GmarketMassSaveScheduler {
  private final MassSaveAplicationService service;

  @Value("${com.ebaykorea.payback.scheduler.mass-save.max-rows}")
  private final int maxRows;
  @Value("${com.ebaykorea.payback.scheduler.mass-save.max-retry-count}")
  private final int maxRetryCount;

  public GmarketMassSaveScheduler(
      final MassSaveAplicationService service,
      @Value("${com.ebaykorea.payback.scheduler.mass-save.max-rows}") final int maxRows,
      @Value("${com.ebaykorea.payback.scheduler.mass-save.max-retry-count}") final int maxRetryCount) {
    this.service = service;
    this.maxRows = maxRows;
    this.maxRetryCount = maxRetryCount;
  }

  @Scheduled(
      initialDelayString = "${com.ebaykorea.payback.scheduler.mass-save.initialDelay}" ,
      fixedDelayString = "${com.ebaykorea.payback.scheduler.mass-save.fixedDelay}",
      timeUnit = TimeUnit.SECONDS)
  public void requestMassSave() {
    service.requsetMassSave(maxRows, maxRetryCount);
  }

}
