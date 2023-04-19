package com.ebaykorea.payback.batch.grocery

import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointCancelRequest

class SsgPointCancelRequestGrocery {

  static def SsgPointCancelRequest_생성(Map map = [:]) {
    new SsgPointCancelRequest().tap {
      clientId = (map.clientId ?: "clientId") as String
      apiKey = (map.apiKey ?: "apiKey") as String
      tokenId = (map.tokenId ?: "tokenId" ) as String
      reqTrcNo = (map.reqTrcNo ?: "trcNo" ) as String
      msgText = (map.msgText ?: "msgText" ) as String
      tradeGbCd = (map.tradeGbCd ?: "tradeGbCd") as String
      tradeGbCd = (map.tradeGbCd ?: "tradeGbCd") as String
      busiDt = (map.busiDt ?: "busiDt") as String
      tradeGentdDt = (map.tradeGentdDt ?: "tradeGentdDt") as String
      tradeGentdTm = (map.tradeGentdTm ?: "tradeGentdTm") as String
      tradeGentdStcd = (map.tradeGentdStcd ?: "tradeGentdStcd") as String
      tradeGentdPosno = (map.tradeGentdPosno ?: "tradeGentdPosno") as String
      tradeNo = (map.tradeNo ?: "tradeNo") as String
      doByid = (map.doByid ?: "doByid") as String
      cardNo = (map.cardNo ?: "cardNo") as String
      inputFlg = (map.inputFlg ?: "inputFlg") as String
      brchId = (map.brchId ?: "brchId") as String
      recptNo = (map.recptNo ?: "recptNo") as String
      recptSeq = (map.recptSeq ?: "recptSeq") as String
      orgSaleTradeNo = (map.orgSaleTradeNo ?: "orgSaleTradeNo") as String
      otradeTotAmt = (map.otradeTotAmt ?: 100L ) as BigDecimal
      otradeBusiDt = (map.otradeBusiDt ?: "accountDate") as String
      otradeRecptNo = (map.otradeRecptNo ?: "orgReceiptNo") as String
      otradeApprId = (map.otradeApprId ?: "orgPntApprId") as String
    }
  }
}
