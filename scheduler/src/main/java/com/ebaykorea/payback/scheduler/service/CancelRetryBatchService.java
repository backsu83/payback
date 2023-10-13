package com.ebaykorea.payback.scheduler.service;

import com.ebaykorea.payback.scheduler.client.PaybackApiClient;
import com.ebaykorea.payback.scheduler.client.dto.payback.CancelRequestDto;
import com.ebaykorea.payback.scheduler.domain.constant.OrderSiteType;
import com.ebaykorea.payback.scheduler.repository.opayreward.CancelConsumerFailRepository;
import com.ebaykorea.payback.scheduler.repository.opayreward.entity.ssg.CancelConsumerFailEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static java.util.Objects.isNull;
import static org.hibernate.internal.util.collections.CollectionHelper.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelRetryBatchService {
    public static final String updOprt = "cancelRetryScheduler";
    public static final String StatusFail = "Fail";
    public static final String StatusSuccess = "Success";
    private final CancelConsumerFailRepository cancelConsumerFailRepository;
    private final PaybackApiClient paybackApiClient;
    private final ExecutorService taskExecutor;

    public void retryCancels(final Long maxTryCount) {
        final List<CancelConsumerFailEntity> cancels = cancelConsumerFailRepository.findTop100ByStatusAndTryCount(StatusFail, maxTryCount);

        if (isEmpty(cancels)) {
            log.info("scheduler - No data for failed cancel consumer");
            return;
        }

        cancels.forEach(cancel -> {
            final var request = CancelRequestDto.builder()
                    .siteType(cancel.getSiteCode() == "G" ? OrderSiteType.Gmarket : OrderSiteType.Auction)
                    .packNo(cancel.getPackNo())
                    .buyerId(cancel.getBuyerId())
                    .build();

            var response = (CompletableFuture
                    .supplyAsync(() -> paybackApiClient.cancelSsgPoint(cancel.getOrderNo(), request), taskExecutor)
                    .exceptionally(ex -> {
                        fail(cancel.getSiteCode(), cancel.getOrderNo(), cancel.getTryCount(), ex.getMessage());
                        log.error("scheduler - fail to cancel api siteCode:{} / orderNo:{} / error:{}",
                                cancel.getSiteCode(),
                                cancel.getOrderNo(),
                                ex.getLocalizedMessage());
                        return null;
                    }));

            if (!isNull(response)){
                success(cancel.getSiteCode(), cancel.getOrderNo());
            }
        });
    }

    private void success(String siteCode, Long orderNo) {
        cancelConsumerFailRepository.save(
                CancelConsumerFailEntity.builder()
                        .siteCode(siteCode)
                        .orderNo(orderNo)
                        .status(StatusSuccess)
                        .updateOperator(updOprt)
                        .updateDate(Instant.now())
                        .build()
        );
    }

    private void fail(String siteCode, Long orderNo, long retryCount, String message) {
        final var count = retryCount + 1L;
        cancelConsumerFailRepository.save(
                CancelConsumerFailEntity.builder()
                        .siteCode(siteCode)
                        .orderNo(orderNo)
                        .tryCount(count)
                        .responseMessage(message)
                        .updateOperator(updOprt)
                        .updateDate(Instant.now())
                        .build()
        );

    }
}
