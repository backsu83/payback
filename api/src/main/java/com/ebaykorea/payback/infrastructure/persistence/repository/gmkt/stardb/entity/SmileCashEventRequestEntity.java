package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmileCashEventRequestEntity {
  public static final String SAVE = "stardb.dbo.UPGMKT_Reward_InsertSmileCashEvent";
  public static final String FIND = "stardb.dbo.UPGMKT_Reward_SelectSmileCashEvent";

  @Column(name = "REQ_MONEY")
  private BigDecimal requestMoney;

  @Column(name = "REQ_OUTPUT_IMPB_MONEY")
  private BigDecimal requestOutputDisabledMoney;

  @Column(name = "CASH_BALANCE_TYPE")
  private String cashBalanceType;

  @Column(name = "CUST_NO")
  private String custNo;

  @Column(name = "EXPIRE_DT")
  private Timestamp expireDate;

  @Column(name = "REF_NO")
  private long refNo;

  @Column(name = "ERS_NO")
  private int ersNo;

  @Column(name = "REG_ID")
  private String regId;
}
