package com.ebaykorea.payback.consumer.service.mapper.impl;

import com.ebaykorea.payback.consumer.client.dto.PaybackSsgPointCancelRequest;
import com.ebaykorea.payback.consumer.event.OrderSiteType;
import com.ebaykorea.payback.consumer.repository.opayreward.entity.CancelConsumerFailEntity;
import com.ebaykorea.payback.consumer.service.mapper.SsgPointMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("gmarket")
@Component
public class GmarketSsgPointMapper implements SsgPointMapper {
  @Override
  public PaybackSsgPointCancelRequest map(final long packNo) {
    return PaybackSsgPointCancelRequest.builder()
        .packNo(packNo)
        .siteType(OrderSiteType.Gmarket.getShortCode())
        .build();
  }

  @Override
  public CancelConsumerFailEntity map(final Long orderNo, final Long packNo, final long responseCode, final String resultMessage, final String oprt) {
    return CancelConsumerFailEntity.builder()
        .orderNo(orderNo)
        .packNo(packNo)
        .siteType(OrderSiteType.Gmarket.getShortCode())
        .status("FAIL")
        .responseCode(String.valueOf(responseCode))
        .responseMessage(resultMessage)
        .insertOperator(oprt)
        .tryCnt(0L)
        .build();
  }
}
