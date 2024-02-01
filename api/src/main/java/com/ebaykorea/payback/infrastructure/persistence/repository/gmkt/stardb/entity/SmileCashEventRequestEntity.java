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
public class SmileCashEventRequestEntity {
  public static final String SAVE = "stardb.dbo.UPGMKT_Reward_InsertSmileCashEvent";
  public static final String FIND = "stardb.dbo.UPGMKT_Reward_SelectSmileCashEvent";

  @Id
  @Column(name = "SMILEPAY_NO")
  private Long smilePayNo;

  @Column(name = "COMMN_TYPE")
  private String saveIntegrationType;

  @Column(name = "APPR_STATUS")
  private int approvalStatus;

  @Column(name = "TRY_CNT")
  private int tryCount;

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

  @Column(name = "EID")
  private Long eid;

  @Column(name = "ERS_NO")
  private int ersNo;

  @Column(name = "REG_ID")
  private String regId;

  @Column(name = "COMMENTS")
  private String comments;

  @Column(name = "CONTR_NO")
  private long orderNo;
}
