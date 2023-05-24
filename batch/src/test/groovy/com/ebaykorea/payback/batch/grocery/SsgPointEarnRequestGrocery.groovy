package com.ebaykorea.payback.batch.grocery

import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointEarnRequest
import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointPayInfo
import com.ebaykorea.payback.batch.util.PaybackInstants

class SsgPointEarnRequestGrocery {

  static def SsgPointEarnRequest_생성(Map map = [:]) {
    new SsgPointEarnRequest().tap {
      clientId = (map.clientId ?: "clientId") as String
      apiKey = (map.apiKey ?: "apiKey") as String
      tokenId = (map.tokenId ?: "tokenId") as String
      reqTrcNo = (map.reqTrcNo ?: "trcNo" ) as String
      msgText = (map.msgText ?: "APITRN0121" ) as String
      tradeGbCd = (map.tradeGbCd ?: "200020") as String
      busiDt = (map.busiDt ?: PaybackInstants.getStringFormatBy("yyyyMMdd")) as String
      tradeGentdDt = (map.tradeGentdDt ?: PaybackInstants.getStringFormatBy("MMdd")) as String
      tradeGentdTm = (map.tradeGentdTm ?: "000000") as String
      tradeGentdStcd = (map.tradeGentdStcd ?: "0000") as String
      tradeGentdPosno = (map.tradeGentdPosno ?: "0000") as String
      tradeNo = (map.tradeNo ?: "tradeNo") as String
      doByid = (map.doByid ?: "000000") as String
      cardNo = (map.cardNo ?: "cardNo") as String
      inputFlg = (map.inputFlg ?: "O") as String
      brchId = (map.brchId ?: "brchId") as String
      recptNo = (map.recptNo ?: "recptNo") as String
      recptSeq = (map.recptSeq ?: "0000") as String
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
