package com.ebaykorea.payback.batch.client.ssgpoint.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SsgPointCancelRequest {
  private String clientId;
  private String apiKey;
  private String tokenId;
  private String reqTrcNo;
  private String msgText;
  private String tradeGbCd;
  private String busiDt;
  private String tradeGentdDt;
  private String tradeGentdTm;
  private String tradeGentdStcd;
  private String tradeGentdPosno;
  private String tradeNo;
  private String doByid;
  private String cardNo;
  private String inputFlg;
  private String brchId;
  private String recptNo;
  private String recptSeq;

  private BigDecimal otradeTotAmt;
  private String otradeBusiDt;
  private String otradeRecptNo;
  private String otradeApprId;
  private String orgSaleTradeNo;

  public String getRequestDate() {
    return busiDt + tradeGentdTm;
  }
}
