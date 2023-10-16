package com.ebaykorea.payback.scheduler.service;

import static com.ebaykorea.payback.scheduler.domain.constant.TenantCode.GMARKET_TENANT;
import static com.ebaykorea.payback.scheduler.service.entity.ProcessType.COMPLETED;
import static com.ebaykorea.payback.scheduler.service.entity.ProcessType.FAIL;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.ebaykorea.payback.scheduler.repository.stardb.CashbackOrderBatchRepository;
import com.ebaykorea.payback.scheduler.client.PaybackApiClient;
import com.ebaykorea.payback.scheduler.client.dto.payback.PaybackRequestDto;
import com.ebaykorea.payback.scheduler.client.dto.payback.PaybackResponseDto;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile(GMARKET_TENANT)
@Slf4j
@Service
@RequiredArgsConstructor
public class PaybackBatchService {
  public static final String updOprt = "paybackScheduler";
  private final CashbackOrderBatchRepository cashbackOrderBatchRepository;
  private final PaybackApiClient paybackApiClient;
  private final ExecutorService taskExecutor;

  public void updateRecords(final Long maxTryCount) {

    final List<CompletableFuture<PaybackResponseDto>> paybacksFuture = Lists.newArrayList();
    final var records = cashbackOrderBatchRepository.findNoCompleted(maxTryCount);

    if (isEmpty(records)) {
      log.info("scheduler - cashback not found records");
      return;
    }

    records.forEach(unit -> {
      final var request = PaybackRequestDto.builder()
          .orderKey(unit.getOrderKey())
          .txKey(unit.getTxKey())
          .build();

      paybacksFuture.add(CompletableFuture
          .supplyAsync(() -> paybackApiClient.saveCashbacks(request), taskExecutor)
          .exceptionally(ex -> {
            fail(unit.getOrderKey(), unit.getTxKey(), unit.getRetryCount(), ex.getMessage());
            log.error("scheduler - fail to payback api orderKey:{} / txKey:{} / error:{}",
                unit.getOrderKey(),
                unit.getTxKey(),
                ex.getLocalizedMessage());
            return null;
          }));
    });

    final var paybacks = CompletableFuture.allOf(
            paybacksFuture.toArray(new CompletableFuture[paybacksFuture.size()]))
        .thenApply(s -> paybacksFuture.stream()
            .map(CompletableFuture::join)
            .collect(Collectors.toList())
        ).join();

    success(paybacks);
  }

  private void success(List<PaybackResponseDto> paybacks) {
    paybacks.stream()
        .filter(Objects::nonNull)
        .forEach(unit -> cashbackOrderBatchRepository.updateStatus(
            unit.getData().getOrderKey(),
            unit.getData().getTxKey(),
            COMPLETED.name(),
            0L,
            unit.getMessage(), updOprt)
        );
  }

  private void fail(String orderKey, String txKey, long retryCount, String message) {
    final var count = retryCount + 1L;
    cashbackOrderBatchRepository.updateStatus(orderKey, txKey, FAIL.name(), count, message, updOprt);
  }
}
