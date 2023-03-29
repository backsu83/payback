package com.ebaykorea.payback.batch.config.client.ssgpoint.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
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
  private String add_gb;
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
}
