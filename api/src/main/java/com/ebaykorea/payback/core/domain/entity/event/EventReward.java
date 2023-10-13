package com.ebaykorea.payback.core.domain.entity.event;

import com.ebaykorea.payback.core.domain.constant.EventType;
import lombok.Value;

@Value
public class EventReward {
  Long requestNo;
  String requestId;
  EventType eventType;
}
