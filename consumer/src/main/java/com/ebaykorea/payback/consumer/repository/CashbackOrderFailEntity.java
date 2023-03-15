package com.ebaykorea.payback.consumer.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashbackOrderFailEntity {
  @Column(name = "ORDER_KEY")
  private String orderKey;

  @Column(name = "TX_KEY")
  private String txKey;

  @Column(name = "RSP_CD")
  private Long responseCode;

  @Column(name = "RSP_MSG")
  private String message;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "TRY_CNT")
  private Long retryCount;

  @Column(name = "INS_OPRT")
  private String insOprt;

  @Column(name = "INS_DATE")
  private Timestamp insDate;

  @Column(name = "UPD_OPRT")
  private String updOprt;

  @Column(name = "UPD_DATE")
  private Timestamp updDate;
}
