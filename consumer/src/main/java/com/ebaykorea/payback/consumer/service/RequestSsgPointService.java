package com.ebaykorea.payback.consumer.service;

import com.ebaykorea.payback.consumer.client.PaybackApiClient;
import com.ebaykorea.payback.consumer.client.dto.PaybackSsgPointCancelRequest;
import com.ebaykorea.payback.consumer.client.dto.PaybackSsgPointCancelResponse;
import com.ebaykorea.payback.consumer.event.OrderSiteType;
import com.ebaykorea.payback.consumer.repository.opayreward.CancelConsumerFailRepository;
import com.ebaykorea.payback.consumer.repository.opayreward.entity.CancelConsumerFailEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestSsgPointService {

  private final PaybackApiClient paybackApiClient;
  private final CancelConsumerFailRepository cancelConsumerFailRepository;

  private final static Logger moALogger = LoggerFactory.getLogger("MoALogger");
  private static final long FAIL = -1L;

  public void cancelSsgPointGmarket(final Long packNo , final List<Long> orderNos) {
    for (Long orderNo : orderNos) {
      try {
        paybackApiClient.cancelSsgPoint(orderNo,
              PaybackSsgPointCancelRequest.builder()
                  .packNo(packNo)
                  .siteType(OrderSiteType.Gmarket.getShortCode())
                  .build())
            .filter(PaybackSsgPointCancelResponse::isNotSuccess)
            .ifPresent(result -> saveError(orderNo, packNo, OrderSiteType.Gmarket, result.getCode(),result.getMessage(),"cancelSsgPointGmarket"));
      } catch (Exception ex) {
        saveError(orderNo, packNo, OrderSiteType.Gmarket ,FAIL, ex.getMessage(), "cancelSsgPointGmarket");
      }
    }
  }

  public void cancelSsgPointAuction(final long packNo, final long orderNo) {
    try {
      paybackApiClient.cancelSsgPoint(orderNo,
              PaybackSsgPointCancelRequest.builder()
                  .packNo(packNo)
                  .siteType(OrderSiteType.Auction.getShortCode())
                  .build())
          .filter(PaybackSsgPointCancelResponse::isNotSuccess)
          .ifPresent(result -> saveError(orderNo, packNo, OrderSiteType.Auction, result.getCode(),result.getMessage(),"cancelSsgPointAuction"));
    } catch (Exception ex) {
      saveError(orderNo, packNo, OrderSiteType.Auction, FAIL, ex.getMessage(), "cancelSsgPointAuction");
    }
  }

  public void saveError(
      final Long orderNo,
      final Long packNo,
      final OrderSiteType siteType,
      final long responseCode,
      final String resultMessage,
      final String oprt
  ) {
    moALogger.error("ssg-points api 처리 실패 : {}, {}, {}, {}", orderNo, packNo, responseCode, resultMessage);
    cancelConsumerFailRepository.save(CancelConsumerFailEntity.builder()
            .orderNo(orderNo)
            .packNo(packNo)
            .siteType(siteType.getShortCode())
            .status("FAIL")
            .responseCode(String.valueOf(responseCode))
            .responseMessage(resultMessage)
            .insertOperator(oprt)
            .tryCnt(0L)
        .build());
  }
}
