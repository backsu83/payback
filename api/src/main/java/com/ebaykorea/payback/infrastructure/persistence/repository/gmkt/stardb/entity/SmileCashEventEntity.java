package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity;

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
}
