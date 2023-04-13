package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.core.domain.constant.OrderSiteType
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointOrigin
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointAuctionState
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointGmarketState
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointState
import com.ebaykorea.payback.core.dto.ssgpoint.CancelSsgPointRequestDto
import com.ebaykorea.payback.core.dto.ssgpoint.SaveSsgPointRequestDto
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTargetResponseDto

import java.time.Instant

class SsgPointGrocery {
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
      orderDate = (map.orderDate ?: Instant.parse("2023-04-11T13:00:00.00Z")) as Instant
      scheduleDate = (map.scheduleDate ?: "2023-04-16T13:00:00Z") as String
      pntApprId = (map.pntApprId ?: "pntApprId") as String
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

  static def SsgPoint_생성(Map map = [:]) {
    SsgPoint.of(
        (map.packNo ?: 1L) as Long,
        (map.buyerNo ?: "buyerId") as String,
        (map.orderDate ?: TestConstant.SSGPOINT_ORDER_DATE) as Instant,
        (map.siteType ?: OrderSiteType.Gmarket) as OrderSiteType,
        (map.ssgPointUnits ?: [SsgPointUnit_준비상태_생성()]) as List<SsgPointUnit>
    )
  }

  static def SsgPointUnit_준비상태_생성(Map map = [:]) {
    SsgPointUnit.readyUnit(
        (map.orderNo ?: 1L) as Long,
        (map.payAmount ?: 1000L) as BigDecimal,
        (map.saveAmount ?: 1000L) as BigDecimal,
        (map.scheduleDate ?: TestConstant.SSGPOINT_SCHEDULE_DATE) as Instant,

        (map.isPolicy) as Boolean,
        (map.state ?: SsgPointGmarketState_생성()) as SsgPointState,
        (map.pointOrigin ?: null) as SsgPointOrigin,
        (map.adminId ?: "adminId") as String
    )
  }
  
  static def SsgPointGmarketState_생성(Map map = [:]) {
    new SsgPointGmarketState()
  }
  static def SsgPointAuctionState_생성(Map map = [:]) {
    new SsgPointAuctionState()
  }

  static def SsgPointUnit_취소상태_생성(Map map = [:]) {
    SsgPointUnit.cancelUnit(
        (map.orderNo ?: 1L) as Long,
        (map.payAmount ?: 1000L) as BigDecimal,
        (map.saveAmount ?: 1000L) as BigDecimal,
        (map.scheduleDate ?: TestConstant.SSGPOINT_SCHEDULE_DATE) as Instant,

        (map.isPolicy) as Boolean,
        (map.state ?: SsgPointGmarketState) as SsgPointState,
        (map.pointOrigin ?: null) as SsgPointOrigin,
        (map.adminId ?: "adminId") as String
    )
  }
  static def SsgPointUnit_보류상태_생성(Map map = [:]) {
    SsgPointUnit.withHoldUnit(
        (map.orderNo ?: 1L) as Long,
        (map.payAmount ?: 1000L) as BigDecimal,
        (map.saveAmount ?: 1000L) as BigDecimal,
        (map.scheduleDate ?: TestConstant.SSGPOINT_SCHEDULE_DATE) as Instant,

        (map.isPolicy) as Boolean,
        (map.state ?: SsgPointGmarketState) as SsgPointState,
        (map.pointOrigin ?: null) as SsgPointOrigin,
        (map.adminId ?: "adminId") as String
    )
  }
}
