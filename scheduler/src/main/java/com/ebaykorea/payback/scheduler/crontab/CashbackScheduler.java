package com.ebaykorea.payback.scheduler.crontab;

import com.ebaykorea.payback.scheduler.service.CashbackSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CashbackScheduler {

  private final CashbackSchedulerService cashbackSchedulerService;

  @Scheduled(cron="0/10 * * * * *")
  public void init() {
    // TODO 멀티 서버로 배포될 경우 중복 실행 방지 로직 필요
    log.info("scheduler start ...");
    cashbackSchedulerService.saveCashback();
  }
}
