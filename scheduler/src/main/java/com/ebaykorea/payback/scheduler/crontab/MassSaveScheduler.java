package com.ebaykorea.payback.scheduler.crontab;

import static com.ebaykorea.payback.scheduler.model.constant.TenantCode.AUCTION_TENANT;

import com.ebaykorea.payback.scheduler.service.MassSaveRequestService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile(AUCTION_TENANT)
@Slf4j
@Component
public class MassSaveScheduler {
  private final MassSaveRequestService service;

  @Value("${com.ebaykorea.payback.scheduler.mass-save.max-rows}")
  private final int maxRows;
  @Value("${com.ebaykorea.payback.scheduler.mass-save.max-retry-count}")
  private final int maxRetryCount;

  public MassSaveScheduler(
      final MassSaveRequestService service,
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
    service.requestMassSave(maxRows, maxRetryCount);
  }

  @Scheduled(
      initialDelayString = "${com.ebaykorea.payback.scheduler.mass-save-check.initialDelay}",
      fixedDelayString = "${com.ebaykorea.payback.scheduler.mass-save-check.fixedDelay}",
      timeUnit = TimeUnit.SECONDS)
  public void massSaveCheck() {
    service.checkSmileCashStatusThenUpdateResult(maxRows, maxRetryCount);
  }
}
