package com.ebaykorea.payback.scheduler.domain.service;

import static com.ebaykorea.payback.scheduler.domain.entity.ProcessType.COMPLETED;

import com.ebaykorea.payback.scheduler.infrastructure.gateway.client.PaybackApiClient;
import com.ebaykorea.payback.scheduler.infrastructure.gateway.dto.PaybackRequestDto;
import com.ebaykorea.payback.scheduler.infrastructure.gateway.dto.PaybackResponseDto;
import java.util.ArrayList;
import java.util.List;
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

  private final PaybackBatchRepository paybackSchedulerRepository;
  private final PaybackApiClient paybackApiClient;
  private final ExecutorService taskExecutor;

  public void saveCashback() {

    List<CompletableFuture<PaybackResponseDto>> taskList = new ArrayList<>();

    //대상건 조회
    final var paySchedulers = paybackSchedulerRepository.findOrderKeyAndTxKey();

    paySchedulers
        .stream()
        .filter(f->!f.getStatus().equals(COMPLETED) && f.getRetryCount() <= 3)
        .forEach( unit-> {
      final var request = PaybackRequestDto.builder()
          .orderKey(unit.getOrderKey())
          .txKey(unit.getTxKey())
          .build();

      try {
        CompletableFuture<PaybackResponseDto> future = CompletableFuture.supplyAsync(
            () -> paybackApiClient.saveCashbacks(request),
            taskExecutor);
        taskList.add(future);
      } catch (Exception ex) {
        //TODO 실패건에 대한 처리
        log.error("scheduler fail to payback api orderKey:{} error:{}"
            , unit.getOrderKey()
            , ex.getLocalizedMessage()
        );
      }
    });

    final var paybackResult = CompletableFuture.allOf(
        taskList.toArray(new CompletableFuture[taskList.size()]))
        .thenApply(Void -> taskList.stream()
            .map(CompletableFuture::join)
            .collect(Collectors.toList()))
        .join();

    //cashback_order_batch 상태값 업데이트
    //api 정상 호출일 경우만 필터 필요
    paybackSchedulerRepository.updateStatus();
  }

}
