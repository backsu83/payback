package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.infrastructure.query.data.CashbackTargetQueryData
import com.ebaykorea.payback.infrastructure.query.data.RewardTargetQueryResult
import com.ebaykorea.payback.infrastructure.query.data.SmileCardQueryData
import com.ebaykorea.payback.infrastructure.query.data.SsgPointTargetQueryData

import java.time.Instant

class RewardTargetQueryResultGrocery {
  static def RewardTargetQueryResult_생성(Map map = [:]) {
    RewardTargetQueryResult.of(
        (map.smileCard ?: SmileCardQueryData.EMPTY) as SmileCardQueryData,
        (map.ssgPoint ?: SsgPointTargetQueryData.EMPTY) as SsgPointTargetQueryData,
        (map.cashbackTargets ?: []) as List<CashbackTargetQueryData>
    )
  }

  static def SmileCardQueryData_생성(Map map = [:]) {
    SmileCardQueryData.of(
        (map.smileCardCashbackAmount ?: 0) as BigDecimal,
        (map.smileCardAdditionalSaveAmount ?: 0) as BigDecimal
    )
  }

  static def SsgPointTargetQueryData_생성(Map map = [:]) {
    new SsgPointTargetQueryData(
        (map.totalAmount ?: 0) as BigDecimal,
        (map.expectSaveDate ?: TestConstant.SSGPOINT_SCHEDULE_DATE) as Instant,
    )
  }

  static def CashbackTargetQueryData_생성(Map map = [:]) {
    new CashbackTargetQueryData().tap{
      cashbackType = (map.cashbackType ?: "Unknown") as String
      totalAmount = (map.totalAmount ?: 0) as BigDecimal
      expectSaveDate = (map.expectSaveDate ?: TestConstant.USE_ENABLE_DATE) as Instant
    }
  }
}
