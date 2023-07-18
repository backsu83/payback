package com.ebaykorea.payback.consumer.service;

import com.ebaykorea.payback.consumer.client.PaybackApiClient;
import com.ebaykorea.payback.consumer.client.dto.PaybackSsgPointCancelResponse;
import com.ebaykorea.payback.consumer.repository.opayreward.CancelConsumerFailRepository;

import java.util.List;

import com.ebaykorea.payback.consumer.service.mapper.SsgPointMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CancelSsgPointService {
  private final PaybackApiClient paybackApiClient;
  private final CancelConsumerFailRepository cancelConsumerFailRepository;
  private final SsgPointMapper ssgPointMapper;

  private final static Logger moALogger = LoggerFactory.getLogger("MoALogger");
  private static final long FAIL = -1L;

  public void cancelSsgPoint(Long packNo, List<Long> orderNos) {
    orderNos.forEach(orderNo -> {
      try {
        paybackApiClient.cancelSsgPoint(orderNo, ssgPointMapper.map(packNo))
            .filter(PaybackSsgPointCancelResponse::isNotSuccess)
            .ifPresent(result -> saveError(orderNo, packNo, result.getCode(), result.getMessage()));
      } catch (Exception ex) {
        saveError(orderNo, packNo, FAIL, ex.getMessage());
      }
    });
  }

  private void saveError(
      final Long orderNo,
      final Long packNo,
      final long responseCode,
      final String resultMessage
  ) {
    moALogger.error("ssg-points api 처리 실패 : {}, {}, {}, {}", orderNo, packNo, responseCode, resultMessage);
    final var entity = ssgPointMapper.map(orderNo, packNo, responseCode, resultMessage, "cancelSsgPointGmarket");
    cancelConsumerFailRepository.save(entity);
  }
}
