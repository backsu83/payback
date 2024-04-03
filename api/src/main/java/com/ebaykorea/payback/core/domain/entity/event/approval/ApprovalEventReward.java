package com.ebaykorea.payback.core.domain.entity.event.approval;

import java.math.BigDecimal;
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

//  private int bizType; //TODO: 지마켓 작업 이후 추가
//  private String bizKey; //TODO: 지마켓 작업 이후 추가

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
