package com.ebaykorea.payback.scheduler.repository.maindb2ex.entity;

import com.ebaykorea.payback.scheduler.model.constant.AuctionSmileCashEventType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

  public static final String INSERT = "maindb2ex.dbo.UPIAC_Escrow_SmileCashSaveApproval_Insert";

  @Id
  @Column(name = "IAC_TXID")
  private long txId;

  @Column(name = "SMILECASH_TXID")
  private String smileCashTxId;

  @Column(name = "SMILE_MEMB_KEY")
  private String smileUserKey;

  @Column(name = "TXN_TYPE")
  private int txnType;

  @Column(name = "SMILECASH_TXN_DATE")
  private Timestamp transactionDate;

  @Column(name = "SMILECASH_TYPE")
  private int smileCashType;

  @Column(name = "APPR_AMNT")
  private BigDecimal saveAmount;

  @Column(name = "SMILECASH_EXPIRE_DATE")
  private Timestamp expireDate;

  @Column(name = "DIFF_PROC_BASE_DATE")
  private Timestamp diffProcBaseDate;

  @Column(name = "DIFF_PROC_IS")
  private int diffProcIs;

  @Column(name = "IAC_REASON_CODE")
  private String auctionSmileCashEventType;

  @Column(name = "IAC_REASON_COMMENT")
  private String reasonComment;

  @Column(name = "IAC_REASON_ADD")
  private String additionalReasonComment;

  @Column(name = "IAC_BIZ_TYPE")
  private int bizType;

  @Column(name = "IAC_BIZ_KEY")
  private String bizKey;

  @Column(name = "INS_OPRT")
  private String insertOperator;

}
