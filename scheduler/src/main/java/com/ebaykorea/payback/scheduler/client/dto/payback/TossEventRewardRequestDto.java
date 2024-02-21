package com.ebaykorea.payback.scheduler.client.dto.payback;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TossEventRewardRequestDto {
  private long requestNo;
  private String memberKey;
  private BigDecimal saveAmount;
}
