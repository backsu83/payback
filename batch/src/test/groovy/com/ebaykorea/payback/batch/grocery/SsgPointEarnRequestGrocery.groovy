package com.ebaykorea.payback.batch.grocery

import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointEarnRequest
import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointPayInfo

class SsgPointEarnRequestGrocery {

  static def SsgPointEarnRequest_생성(Map map = [:]) {
    new SsgPointEarnRequest().tap {
      clientId = (map.clientId ?: "clientId") as String
      apiKey = (map.apiKey ?: "apiKey") as String
      tokenId = (map.tokenId ?: "tokenId") as String
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
      pntNoAddProdAmt = (map.pntNoAddProdAmt ?: 0L) as BigDecimal
      totAmt = (map.totAmt ?: 100L) as BigDecimal
      orgSaleTradeNo = (map.orgSaleTradeNo ?: "orgSaleTradeNo") as String
      payInfo = (map.payInfo ?: [SsgPointPayInfo_생성()]) as List
    }
  }

  static def SsgPointPayInfo_생성(Map map = [:]) {
    new SsgPointPayInfo().tap {
      payType = (map.payType ?: "A00011") as String
      payAmt = (map.payAmt ?: 100L) as BigDecimal
      payGb = (map.payGb ?: "00") as String
    }
  }
}
