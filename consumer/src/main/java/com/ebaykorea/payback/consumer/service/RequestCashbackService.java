package com.ebaykorea.payback.consumer.service;

import com.ebaykorea.payback.consumer.client.PaybackApiClient;
import com.ebaykorea.payback.consumer.client.dto.PaybackRequestDto;
import com.ebaykorea.payback.consumer.client.dto.PaybackResponseDto;
import com.ebaykorea.payback.consumer.repository.CashbackOrderFailRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestCashbackService {
  private final PaybackApiClient paybackApiClient;
  private final CashbackOrderFailRepository repository;

  private final static Logger moALogger = LoggerFactory.getLogger("MoALogger");
  private static final long FAIL = -1L;

  public void requestCashback(final String txKey, final String orderKey) {
    try {
      paybackApiClient.saveCashbacks(PaybackRequestDto.builder().txKey(txKey).orderKey(orderKey).build())
          .filter(PaybackResponseDto::isNotSuccess)
          .ifPresent(result -> saveError(txKey, orderKey, result.getCode(), result.getMessage(), "RequestCashbackService"));
    } catch (Exception ex) {
      saveError(txKey, orderKey, FAIL, ex.getMessage(), "RequestCashbackService");
    }
  }

  public void saveError(
      final String txKey,
      final String orderKey,
      final long responseCode,
      final String resultMessage,
      final String oprt
  ) {
    moALogger.error("payback api 처리 실패 : {}, {}, {}, {}", txKey, orderKey, responseCode, resultMessage);
    repository.save(txKey, orderKey, responseCode, resultMessage, oprt);
  }
}
