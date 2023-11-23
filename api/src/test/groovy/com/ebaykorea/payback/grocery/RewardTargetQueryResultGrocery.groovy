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
        (map.smileCard ?: null) as SmileCardQueryData,
        (map.ssgPoints ?: []) as List<SsgPointTargetQueryData>,
        (map.cashbackTargets ?: []) as List<CashbackTargetQueryData>
    )
  }

  static def SmileCardQueryData_생성(Map map = [:]) {
    SmileCardQueryData.of(
        (map.type ?: "") as String,
        (map.saveAmount ?: 0) as BigDecimal,
        (map.expectSaveDays ?: null) as Integer,
        (map.additionalSaveAmount ?: 0) as BigDecimal,
        (map.additionalExpectSaveDate ?: null) as Instant
    )
  }

  static def SsgPointTargetQueryData_생성(Map map = [:]) {
    SsgPointTargetQueryData.of(
        (map.expectSaveDate ?: TestConstant.SSGPOINT_SCHEDULE_DATE) as Instant,
        (map.saveAmount ?: 0) as BigDecimal
    )
  }

  static def CashbackTargetQueryData_생성(Map map = [:]) {
    new CashbackTargetQueryData().tap {
      cashbackType = (map.cashbackType ?: "Unknown") as String
      saveAmount = (map.saveAmount ?: 0) as BigDecimal
      expectSaveDate = (map.expectSaveDate ?: TestConstant.USE_ENABLE_DATE) as Instant
    }
  }
}
