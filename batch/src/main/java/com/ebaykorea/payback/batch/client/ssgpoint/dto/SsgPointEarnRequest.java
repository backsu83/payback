package com.ebaykorea.payback.batch.client.ssgpoint.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SsgPointEarnRequest {
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
  private BigDecimal pntNoAddProdAmt;
  private BigDecimal totAmt;
  private String orgSaleTradeNo;
  private List<SsgPointPayInfo> payInfo;

  public String getRequestDate() {
    return busiDt + tradeGentdTm;
  }

}
