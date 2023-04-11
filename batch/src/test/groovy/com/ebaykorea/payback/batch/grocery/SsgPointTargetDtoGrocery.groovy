package com.ebaykorea.payback.batch.grocery

import com.ebaykorea.payback.batch.domain.SsgPointTargetDto
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType
import com.ebaykorea.payback.batch.domain.constant.PointStatusType
import com.ebaykorea.payback.batch.domain.constant.PointTradeType

class SsgPointTargetDtoGrocery {

  static def SsgPointTargetDto_생성(Map map = [:]) {
    new SsgPointTargetDto().tap {
      orderNo = (map.orderNo ?: 111L) as Long
      buyerId = (map.buyerId ?: "buyerId") as String
      siteType = (map.siteType ?: OrderSiteType.Gmarket ) as OrderSiteType
      tradeType = (map.tradeType ?: PointTradeType.Save ) as PointTradeType
      status = (map.status ?: PointStatusType.Ready ) as PointStatusType
      receiptNo = (map.receiptNo ?: "recptNo") as String
      pntApprId = (map.pntApprId ?: "pntApprId") as String
      saveAmount = (map.saveAmount ?: 0L) as BigDecimal
      pointToken = (map.pointToken ?: "pointToken") as String
      accountDate = (map.accountDate ?: "accountDate") as String
      requestDate = (map.requestDate ?: "requestDate") as String
      responseCode = (map.responseCode ?: "responseCode") as String
    }
  }
}
