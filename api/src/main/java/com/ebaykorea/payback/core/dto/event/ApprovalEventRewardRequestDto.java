package com.ebaykorea.payback.core.dto.event;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalEventRewardRequestDto {
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
