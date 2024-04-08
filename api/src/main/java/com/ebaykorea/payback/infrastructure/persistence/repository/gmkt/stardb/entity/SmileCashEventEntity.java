package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity;

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
  public static final String FIND = "stardb.dbo.UPGMKT_Reward_SelectSmileCashEvent";
  public static final String FIND_BY_SMILEPAY_NO = "stardb.dbo.UPGMKT_Reward_SelectSmileCashEventBySmilePayNo";
  public static final String UPDATE = "stardb.dbo.UP_GMKT_Reward_UpdateSmileCashEvent";

  @Id
  @Column(name = "SMILEPAY_NO")
  private Long smilePayNo;

  @Column(name = "APPR_STATUS")
  private int status;

  @Column(name = "RET_CD")
  private String returnCode;

  @Column(name = "REQ_MONEY")
  private BigDecimal requestMoney;

  @Column(name = "CERT_APPR_ID")
  private String certApprId;

  @Column(name = "RESULT_OUTPUT_IMPB_MONEY")
  private BigDecimal resultOutputImpbMoney;

  @Column(name = "RET_EXPIRE_DT")
  private Timestamp retExpireDate;

  @Column(name = "APPR_DT")
  private Timestamp saveDate;

  private static final int SAVED = 50;
  public boolean canBeApproved() {
    return status != 50;
  }
}
