package com.ebaykorea.payback.core.domain.entity.event;

import com.ebaykorea.payback.core.domain.constant.EventType;
import lombok.Value;

@Value
public class TossRewardRequestResult {
  Long requestNo;
  String requestId;
  EventType eventType;
}
