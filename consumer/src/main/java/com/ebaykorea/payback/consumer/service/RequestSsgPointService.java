package com.ebaykorea.payback.consumer.service;

import com.ebaykorea.payback.consumer.client.PaybackApiClient;
import com.ebaykorea.payback.consumer.client.dto.PaybackSsgPointCancelRequest;
import com.ebaykorea.payback.consumer.event.OrderSiteType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestSsgPointService {

  private final PaybackApiClient paybackApiClient;

  private final static Logger moALogger = LoggerFactory.getLogger("MoALogger");
  private static final long FAIL = -1L;

  public void cancelSsgPointGmarket(final Long packNo , final List<Long> orderNos) {
    for (Long orderNo : orderNos) {
      try {
      paybackApiClient.cancelSsgPoint(PaybackSsgPointCancelRequest.builder()
              .orderNo(orderNo)
              .packNo(packNo)
              .siteType(OrderSiteType.Gmarket)
              .build())
          .ifPresent(result -> saveError(orderNo, packNo, result.getCode(),result.getMessage(),"cancelSsgPointGmarket"));
      } catch (Exception ex) {
        saveError(orderNo, packNo, FAIL, ex.getMessage(), "cancelSsgPointGmarket");
      }
    }
  }

  public void cancelSsgPointAuction(final long packNo, final long orderNo) {
    try {
      paybackApiClient.cancelSsgPoint(PaybackSsgPointCancelRequest.builder()
              .orderNo(orderNo)
              .packNo(packNo)
              .siteType(OrderSiteType.Auction)
              .build())
          .ifPresent(result -> saveError(orderNo, packNo, result.getCode(),result.getMessage(),"cancelSsgPointAuction"));
    } catch (Exception ex) {
      saveError(orderNo, packNo, FAIL, ex.getMessage(), "cancelSsgPointAuction");
    }
  }

  public void saveError(
      final Long orderNo,
      final Long packNo,
      final long responseCode,
      final String resultMessage,
      final String oprt
  ) {
    moALogger.error("ssg-points api 처리 실패 : {}, {}, {}, {}", orderNo, packNo, responseCode, resultMessage);
    //TODO DB저장
  }
}
