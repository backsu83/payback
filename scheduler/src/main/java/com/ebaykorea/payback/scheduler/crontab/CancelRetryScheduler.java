package com.ebaykorea.payback.scheduler.crontab;

import com.ebaykorea.payback.scheduler.service.CancelRetryBatchService;
import com.ebaykorea.payback.scheduler.service.PaybackBatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.ebaykorea.payback.scheduler.domain.constant.TenantCode.GMARKET_TENANT;

@Profile(GMARKET_TENANT)
@Slf4j
@Component
public class CancelRetryScheduler {
    private final CancelRetryBatchService cancelRetryBatchService;
    private final Long maxTryCount;

    public CancelRetryScheduler(
            final CancelRetryBatchService cancelRetryBatchService,
            @Value("${com.ebaykorea.payback.scheduler.data.maxTryCount:5}")final Long maxTryCount
    ) {
        this.cancelRetryBatchService = cancelRetryBatchService;
        this.maxTryCount = maxTryCount;
    }

    @Scheduled(initialDelayString = "${com.ebaykorea.payback.scheduler.data.initialDelay}" , fixedDelayString = "${com.ebaykorea.payback.scheduler.data.fixedDelay}" , timeUnit = TimeUnit.MINUTES)
    public void init() {
        log.info("scheduler start ...");
        cancelRetryBatchService.retryCancels(maxTryCount);
    }
}
