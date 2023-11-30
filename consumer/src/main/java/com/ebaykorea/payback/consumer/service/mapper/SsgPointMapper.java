package com.ebaykorea.payback.consumer.service.mapper;

import com.ebaykorea.payback.consumer.client.dto.PaybackSsgPointCancelRequest;
import com.ebaykorea.payback.consumer.repository.opayreward.entity.CancelConsumerFailEntity;

public interface SsgPointMapper {
  PaybackSsgPointCancelRequest map(long packNo);
  CancelConsumerFailEntity map(Long orderNo, Long packNo, long responseCode, String resultMessage, String oprt);
}
