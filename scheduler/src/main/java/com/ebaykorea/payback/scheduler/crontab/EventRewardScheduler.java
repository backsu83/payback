package com.ebaykorea.payback.scheduler.crontab;

import com.ebaykorea.payback.scheduler.service.EventRewardService;
import com.ebaykorea.saturn.starter.config.SaturnApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventRewardScheduler {

  private final EventRewardService service;

  private final SaturnApplicationProperties tenantProperties;

  @Scheduled(initialDelayString = "${com.ebaykorea.payback.scheduler.data.initialDelay}" , fixedDelayString = "${com.ebaykorea.payback.scheduler.data.fixedDelay}" , timeUnit = TimeUnit.MINUTES)
  public void init() {
    log.info("EventRewardScheduler start ...");
    service.run(tenantProperties.getTenantId());
  }
}
