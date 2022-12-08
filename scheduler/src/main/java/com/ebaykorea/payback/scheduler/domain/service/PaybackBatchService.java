package com.ebaykorea.payback.scheduler.domain.service;

import static com.ebaykorea.payback.scheduler.domain.entity.ProcessType.COMPLETED;
import static com.ebaykorea.payback.scheduler.domain.entity.ProcessType.FAIL;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.ebaykorea.payback.scheduler.infrastructure.gateway.client.PaybackApiClient;
import com.ebaykorea.payback.scheduler.infrastructure.gateway.dto.PaybackRequestDto;
import com.ebaykorea.payback.scheduler.infrastructure.gateway.dto.PaybackResponseDto;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaybackBatchService {

  private final PaybackBatchRepository paybackBatchRepository;
  private final PaybackApiClient paybackApiClient;
  private final ExecutorService taskExecutor;

  public void updateRecords() {

    final List<CompletableFuture<PaybackResponseDto>> paybacksFuture = Lists.newArrayList();
    final var records = paybackBatchRepository.getRecords();
    if(isEmpty(records)) {
      log.info("scheduler - cashback not found records");
      return;
    }

    records.stream()
        .filter(f-> f.getRetryCount() <= 3)
        .forEach( unit-> {
      final var request = PaybackRequestDto.builder()
          .orderKey(unit.getOrderKey())
          .txKey(unit.getTxKey())
          .build();

      paybacksFuture.add(CompletableFuture
          .supplyAsync(() -> paybackApiClient.saveCashbacks(request), taskExecutor)
          .exceptionally(ex -> {
            fail(unit.getOrderKey() , unit.getOrderKey(), unit.getRetryCount());
            log.error("scheduler - fail to payback api orderKey:{} error:{}", unit.getOrderKey(), ex.getLocalizedMessage());
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

  public void success(List<PaybackResponseDto> paybacks) {
    paybacks.stream()
        .filter(f->Objects.nonNull(f.getData()))
        .map(o->o.getData())
        .forEach(unit-> paybackBatchRepository.updateStatus(unit.getOrderKey() , unit.getTxKey(), COMPLETED , 0L));
  }

  public void fail(String orderKey , String txKey , long retryCount) {
    final var count = retryCount + 1L;
    paybackBatchRepository.updateStatus(orderKey , txKey, FAIL , count);
  }
}
