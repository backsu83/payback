package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity;

import java.math.BigDecimal;
import java.time.Instant;
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
public class SmileCashEventEntity {

  @Id
  @Column(name = "SMILEPAY_NO")
  private Long smilePayNo;

  @Column(name = "APPR_STATUS")
  private int status;

  @Column(name = "RET_CD")
  private String returnCode;

  @Column(name = "CERT_DT")
  private Instant saveDate;

  @Column(name = "REQ_MONEY")
  private BigDecimal requestMoney;
}
