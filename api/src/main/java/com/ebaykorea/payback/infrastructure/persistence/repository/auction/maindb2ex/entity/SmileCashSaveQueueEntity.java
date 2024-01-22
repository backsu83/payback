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
public class SmileCashSaveQueueEntity {

  public static final String FIND_BY_BIZ_KEY = "maindb2ex.dbo.UPIAC_Reward_SmileCashSaveQueue_SelectByIacBizKey";
  public static final String SAVE = "maindb2ex.dbo.UPIAC_Reward_SmileCashSaveQueue_Insert";
  public static final String UPDATE_BUDGET = "maindb2ex.dbo.UPIAC_Reward_UpdateAmsSmileCashSaveReq";

  @Id
  @Column(name = "SEQNO")
  private long seqNo;

  @Column(name = "IAC_TXID")
  private long txId;

  @Column(name = "IAC_MEMB_ID")
  private String memberId;

  @Column(name = "IAC_REASON_CODE")
  private String reasonCode;

  @Column(name = "IAC_REASON_COMMENT")
  private String reasonComment;

  @Column(name = "IAC_REASON_ADD")
  private String additionalReasonComment;

  @Column(name = "IAC_BIZ_TYPE")
  private int bizType;

  @Column(name = "IAC_BIZ_KEY")
  private String bizKey;

  @Column(name = "SMILECASH_TYPE")
  private int smileCashType;

  @Column(name = "SAVE_AMNT")
  private BigDecimal saveAmount;

  @Column(name = "SAVE_STATUS")
  private int saveStatus;

  @Column(name = "EXPIRE_DATE")
  private Timestamp expireDate;

  @Column(name = "RETRY_CNT")
  private int retryCount;

  @Column(name = "INS_OPRT")
  private String insertOperator;

  @Column(name = "REFERENCE_KEY")
  private String referenceKey;

  @Column(name = "BUDGET_NO")
  private long budgetNo;
}
