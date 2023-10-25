package com.ebaykorea.payback.scheduler.crontab;

import com.ebaykorea.payback.scheduler.service.EventRewardService;
import com.ebaykorea.payback.scheduler.support.SchedulerUtils;
import com.ebaykorea.saturn.starter.config.SaturnApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static com.ebaykorea.payback.scheduler.support.SchedulerUtils.*;

@Slf4j
@Component
public class EventRewardScheduler {

  private final EventRewardService service;
  private final SaturnApplicationProperties tenantProperties;

  @Value("${com.ebaykorea.payback.scheduler.event-reward.start-date}")
  private final String startDate;
  @Value("${com.ebaykorea.payback.scheduler.event-reward.end-date}")
  private final String endDate;

  public EventRewardScheduler(
      final EventRewardService service,
      final SaturnApplicationProperties tenantProperties,
      @Value("${com.ebaykorea.payback.scheduler.event-reward.start-date}") final String startDate,
      @Value("${com.ebaykorea.payback.scheduler.event-reward.end-date}") final String endDate) {
    this.service = service;
    this.tenantProperties = tenantProperties;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  @Scheduled(initialDelayString = "${com.ebaykorea.payback.scheduler.data.initialDelay}" , fixedDelayString = "${com.ebaykorea.payback.scheduler.data.fixedDelay}" , timeUnit = TimeUnit.MINUTES)
  public void init() {
    final var tenantId = tenantProperties.getTenantId();
    final var start = orElse(startDate, toStringWithDateFormat(now()));
    final var end = orElse(endDate, toStringWithDateFormat(now().plus(1, ChronoUnit.DAYS)));

    log.info(String.format("EventRewardScheduler started with tenantId: %s, startDate: %s, endDate: %s", tenantId, start, end));
    service.run(tenantId, start, end);
  }
}
