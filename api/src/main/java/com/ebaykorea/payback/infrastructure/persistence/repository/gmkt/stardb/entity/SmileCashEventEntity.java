package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmileCashEventEntity {

  @Id
  @Column(name = "SMILEPAY_NO")
  private long smilePayNo;

  @Column(name = "PACK_NO")
  private long packNo;

  @Column(name = "CONTR_NO")
  private long contrNo;

  @Column(name = "COMMN_TYPE")
  private String commnType;

  @Column(name = "TRADE_CD")
  private String tradeCode;

  @Column(name = "SMILECASH_CD")
  private String smileCashCode;

  @Column(name = "REQ_MONEY")
  private BigDecimal requestMoney;

  @Column(name = "REQ_OUTPUT_ENABLE_MONEY")
  private BigDecimal requestOutputEnabledMoney;

  @Column(name = "REQ_OUTPUT_IMPB_MONEY")
  private BigDecimal requestOutputDisabledMoney;

  @Column(name = "CASH_BALANCE_TYPE")
  private String cashBalanceType;

  @Column(name = "CUST_NO")
  private String custNo;

  @Column(name = "COMMENTS")
  private String comments;

  @Column(name = "EXPIRE_DT")
  private Timestamp expireDate;

  @Column(name = "REF_NO")
  private long refNo;

  @Column(name = "ERS_NO")
  private int ersNo;

  @Column(name = "REG_ID")
  private String regId;
}
