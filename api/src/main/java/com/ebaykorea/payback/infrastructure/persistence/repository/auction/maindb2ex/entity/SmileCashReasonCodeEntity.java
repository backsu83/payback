package com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmileCashReasonCodeEntity {
  public static final String FIND = "maindb2ex.dbo.UPIAC_Reward_SmileCashReasonCode_Select";

  @Id
  @Column(name = "IAC_REASON_CODE")
  private String reasonCode;

  @Column(name = "IAC_REASON_COMMENT")
  private String iacReasonComment;

  @Column(name = "ADDITIONAL_COMMENT")
  private String additionalComment;
}
