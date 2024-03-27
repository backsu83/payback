package com.ebaykorea.payback.core.domain.entity.event.approval;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class ApprovalEventReward {
  private long savingNo;
  private String smileUserKey;
  private String authorizationId;
  private String shopTransactionId;
  private String shopOrderNo;
  private String shopManageCode;
  private String shopComment;
  private String promotionId;
  private BigDecimal totalApprovalAmount;
  private String transactionDate;
  private String expireDate;
}
