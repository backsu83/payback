package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.ssgpoint.entity;

import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Persistable;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@IdClass(SsgPointTargetEntityId.class)
@Table(schema = "O_PAYREWARD", name = "SSG_POINT_TARGET")
public class SsgPointTargetEntity extends BaseEntity implements Persistable<SsgPointTargetEntityId> {

  @Id
  @Column(name = "ORDER_NO")
  private Long orderNo;

  @Id
  @Column(name = "BUYER_ID")
  private String buyerId;

  @Id
  @Column(name = "SITE_TYPE")
  private String siteType;

  @Id
  @Column(name = "TRADE_TYPE")
  private String tradeType;

  @Column(name = "RECEIPT_NO")
  private String receiptNo;

  @Column(name = "ORG_RECEIPT_NO")
  private String orgReceiptNo;

  @Column(name = "PNT_APPR_ID")
  private String pntApprId;

  @Column(name = "ORG_PNT_APPR_ID")
  private String orgPntApprId;

  @Column(name = "PAY_AMOUNT")
  private BigDecimal payAmount;

  @Column(name = "SAVE_AMOUNT")
  private BigDecimal saveAmount;

  @Column(name = "POINT_STATUS")
  private String pointStatus;

  @Column(name = "CANCEL_YN")
  private String cancelYn;

  @Column(name = "POINT_TOKEN")
  private String pointToken;

  @Column(name = "ORDER_DATE")
  private Instant orderDate;

  @Column(name = "SCHEDULE_DATE")
  private Instant scheduleDate;

  @Column(name = "REQUEST_DATE")
  private Instant requestDate;

  @Column(name = "ACCOUNT_DATE")
  private String accountDate;

  @Column(name = "RESPONSE_CODE")
  private String responseCode;

  @Column(name = "TRC_NO")
  private String trcNo;

  @Column(name = "TRADE_NO")
  private String tradeNo;

  @Column(name = "PACK_NO")
  private Long packNo;

  @Column(name = "MANUAL_OPRT")
  private String manualOprt;

  @Column(name = "ADMIN_CANCEL_YN")
  private String adminCancelYn;

  @Column(name = "TRY_COUNT")
  private Long tryCount;


  @Override
  public SsgPointTargetEntityId getId() {
    return new SsgPointTargetEntityId(orderNo,buyerId, siteType, tradeType);
  }

  @Override
  public boolean isNew() {
    return true;
  }
}
