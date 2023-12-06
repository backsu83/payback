package com.ebaykorea.payback.scheduler.client.dto.payback;

import com.ebaykorea.payback.scheduler.domain.constant.EventType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EventRewardRequestDto {
  private long requestNo;
  private String memberKey;
  private BigDecimal saveAmount;
  private EventType eventType;
}
