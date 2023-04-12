package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.OrderSiteType
import com.ebaykorea.payback.core.dto.CancelSsgPointRequestDto
import com.ebaykorea.payback.core.dto.SaveSsgPointRequestDto
import com.ebaykorea.payback.core.dto.SsgPointTargetResponseDto

class SaveSsgPointGrocery {
  static def SaveSsgPointRequestDto_생성(Map map = [:]) {
    new SaveSsgPointRequestDto(
        (map.siteType ?: OrderSiteType.Gmarket) as OrderSiteType,
        (map.packNo ?: 1L) as Long,
        (map.buyerId ?: "buyerId") as String,
        (map.orderNo ?: 1L) as Long,
        (map.payAmount ?: 1000L) as BigDecimal,
        (map.saveAmount ?: 1000L) as BigDecimal,
        (map.orderDate ?: "2023-04-11 13:00:00") as String,
        (map.scheduleDate ?: "2023-04-16 13:00:00") as String,
        (map.adminId ?: "adminId") as String
    )
  }

  static def SsgPointTargetResponseDto_생성(Map map = [:]) {
    new SsgPointTargetResponseDto().tap {
      packNo = (map.packNo ?: 1L) as Long
      orderNo = (map.orderNo ?: 1L) as Long
      buyerId = (map.buyerId ?: "buyerId") as String
      pointStatus = (map.pointStatus ?: "RR") as String
      tradeType = (map.tradeType ?: "S") as String
      receiptNo = (map.receiptNo ?: "1") as String
      payAmount = (map.payAmount ?: 1000L) as BigDecimal
      saveAmount = (map.saveAmount ?: 1000L) as BigDecimal
      scheduleDate = (map.scheduleDate ?: "2023-04-16 13:00:00") as String
      adminId = (map.adminId ?: "adminId") as String
    }
  }

  static def CancelSsgPointRequestDto_생성(Map map = [:]) {
    new CancelSsgPointRequestDto(
        (map.siteType ?: OrderSiteType.Gmarket) as OrderSiteType,
        (map.packNo ?: 1L) as Long,
        (map.buyerId ?: "buyerId") as String,
        (map.adminId ?: "adminId") as String
    )
  }
}
