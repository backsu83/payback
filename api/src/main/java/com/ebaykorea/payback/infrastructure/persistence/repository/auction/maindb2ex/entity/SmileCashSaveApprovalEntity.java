package com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmileCashSaveApprovalEntity {
  public static final String FIND_BY_ID = "maindb2ex.dbo.UPIAC_Reward_SelectSmileCashSaveApproval";

  @Id
  @Column(name = "IAC_TXID")
  private long txId;

  @Column(name = "SMILECASH_TXN_DATE")
  private Timestamp saveDate;

  @Column(name = "APPR_AMNT")
  private BigDecimal saveAmount;

  @Column(name = "IAC_REASON_CODE")
  private String reasonCode;

}
