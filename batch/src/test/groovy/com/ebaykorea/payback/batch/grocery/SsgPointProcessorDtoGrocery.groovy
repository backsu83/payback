package com.ebaykorea.payback.batch.grocery

import com.ebaykorea.payback.batch.domain.SsgPointProcessorDto
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType
import com.ebaykorea.payback.batch.domain.constant.PointStatusType
import com.ebaykorea.payback.batch.domain.constant.PointTradeType

class SsgPointProcessorDtoGrocery {

  static def SsgPointProcessorDto_생성(Map map = [:]) {
    new SsgPointProcessorDto().tap {
      orderNo = (map.orderNo ?: 111L) as Long
      buyerId = (map.buyerId ?: "buyerId") as String
      siteType = (map.siteType ?: OrderSiteType.Gmarket ) as OrderSiteType
      tradeType = (map.tradeType ?: PointTradeType.Save ) as PointTradeType
      status = (map.status ?: PointStatusType.Ready ) as PointStatusType
      receiptNo = (map.receiptNo ?: "recptNo") as String
      payAmount = (map.payAmount ?: 100L) as BigDecimal
      cancelYn = (map.cancelYn ?: "cancelYn") as String
      pointToken = (map.pointToken ?: "pointToken") as String
      trcNo = (map.trcNo ?: "trcNo") as String
      tradeNo = (map.tradeNo ?: "tradeNo") as String
      accountDate = (map.accountDate ?: "accountDate") as String
      orgReceiptNo = (map.orgReceiptNo ?: "orgReceiptNo") as String
      orgPntApprId = (map.orgPntApprId ?: "orgPntApprId") as String
      orgSaleTradeNo = (map.orgSaleTradeNo ?: orderNo) as String
    }
  }
}
