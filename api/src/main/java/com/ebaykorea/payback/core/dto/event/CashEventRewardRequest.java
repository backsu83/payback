package com.ebaykorea.payback.core.dto.event;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.constant.ReferenceType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashEventRewardRequest {

  private String requestId;
  private Long requestNo;
  private BigDecimal saveAmount;
  private EventType eventType;
  private ReferenceType caller;
  private String comment;
  private String balanceCode;

}
