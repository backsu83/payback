package com.ebaykorea.payback.core.domain.entity.event.approval;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class ApprovalEventReward {
  private long savingNo;
  private String authorizationId;
  private String shopManageCode;
  private String shopComment;
  private BigDecimal saveAmount;
  private Instant transactionDate;
  private Instant expireDate;
}
