package com.ebaykorea.payback.schedulercluster.repository.stardb.entity;

import com.ebaykorea.payback.schedulercluster.model.constant.GmarketSmileCashEventType;
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
public class SmileCashEventEntity {

  public static final String FIND_TARGETS = "stardb.dbo.UPGMKT_Reward_SmileCashEvent_SelectMassSaveTarget";
  public static final String UPDATE = "stardb.dbo.UP_GMKT_DAEMON_SMILECASH_EVENT_UPDATE_STATE";

  @Id
  @Column(name = "SMILEPAY_NO")
  private Long smilePayNo;

  @Column(name = "COMMN_TYPE")
  private String saveIntegrationType;

  @Column(name = "APPR_STATUS")
  private int approvalStatus;

  @Column(name = "SMILECASH_CD")
  private GmarketSmileCashEventType smilecashCd;

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

  @Column(name = "SUB_SHOP_ID")
  private String subShopId;

  public String getShopOrderId()
  {
    var shopOrderId = "";

    if (orderNo != 0) {
      shopOrderId = String.valueOf(orderNo);
    } else if (eid != 0) {
      shopOrderId = String.valueOf(eid);
    } else if (refNo != 0) {
      shopOrderId = String.valueOf(refNo);
    } else if (ersNo != 0) {
      shopOrderId = String.valueOf(ersNo);
    }
    return shopOrderId;
  }
}
