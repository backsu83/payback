package com.ebaykorea.payback.batch.grocery

import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointCancelRequest
import com.ebaykorea.payback.batch.util.PaybackInstants

class SsgPointCancelRequestGrocery {

  static def SsgPointCancelRequest_생성(Map map = [:]) {
    new SsgPointCancelRequest().tap {
      clientId = (map.clientId ?: "clientId") as String
      apiKey = (map.apiKey ?: "apiKey") as String
      tokenId = (map.tokenId ?: "tokenId" ) as String
      reqTrcNo = (map.reqTrcNo ?: "trcNo" ) as String
      msgText = (map.msgText ?: "APITRN0141" ) as String
      tradeGbCd = (map.tradeGbCd ?: "400080") as String
      busiDt = (map.busiDt ?: PaybackInstants.getStringFormatBy("yyyyMMdd")) as String
      tradeGentdDt = (map.tradeGentdDt ?: PaybackInstants.getStringFormatBy("MMdd")) as String
      tradeGentdTm = (map.tradeGentdTm ?: "000000") as String
      tradeGentdStcd = (map.tradeGentdStcd ?: "0000") as String
      tradeGentdPosno = (map.tradeGentdPosno ?: "0000") as String
      tradeNo = (map.tradeNo ?: "tradeNo") as String
      doByid = (map.doByid ?: "000000") as String
      cardNo = (map.cardNo ?: "pointToken") as String
      inputFlg = (map.inputFlg ?: "O") as String
      brchId = (map.brchId ?: "brchId") as String
      recptNo = (map.recptNo ?: "recptNo") as String
      recptSeq = (map.recptSeq ?: "0000") as String
      orgSaleTradeNo = (map.orgSaleTradeNo ?: "orgSaleTradeNo") as String
      otradeTotAmt = (map.otradeTotAmt ?: 100L ) as BigDecimal
      otradeBusiDt = (map.otradeBusiDt ?: "20230411") as String
      otradeRecptNo = (map.otradeRecptNo ?: "otradeRecptNo") as String
      otradeApprId = (map.otradeApprId ?: "orgPntApprId") as String
    }
  }
}
