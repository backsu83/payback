package com.ebaykorea.payback.consumer.repository.stardb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashbackOrderFailEntity {
  public static final String SAVE = "stardb.dbo.UPGMKT_Payback_CashbackOrderFail_Insert";

  @Column(name = "ORDER_KEY")
  private String orderKey;

  @Column(name = "TX_KEY")
  private String txKey;

  @Column(name = "RSP_CD")
  private Long responseCode;
}
