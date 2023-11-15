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
public class SmileCashEventResultEntity {
  @Column(name = "RET_NO")
  private int result;

  @Column(name = "RET_COMMENT")
  private String comment;

  @Id
  @Column(name = "RET_SMILEPAY_NO")
  private long smilePayNo;
}
