package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.CashbackType
import com.ebaykorea.payback.core.domain.entity.reward.RewardBackendCashbackPolicy
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicy

class RewardGrocery {
  static def RewardCashbackPolicies_생성(Map map = [:]) {
    new RewardCashbackPolicies(
        (map.cashbackPolicies ?: [RewardCashbackPolicy_생성()]) as List<RewardCashbackPolicy>,
        (map.backendCashbackPolicies ?: [RewardBackendCashbackPolicy_생성()]) as List<RewardBackendCashbackPolicy>,
        (map.useEnableDate ?: "2023-10-17") as String,
        (map.smileCardCashbackAmount ?: 0L) as BigDecimal,
        (map.newSmileCardCashbackAmount ?: 0L) as BigDecimal
    )
  }

  static def RewardCashbackPolicy_생성(Map map = [:]) {
    new RewardCashbackPolicy(
        (map.policyKey ?: 1L) as long,
        (map.cashbackCd ?: CashbackType.Item) as CashbackType,
        (map.cashbackAmount ?: 1000) as Integer,
        (map.cashbackSeq ?: 1) as Integer,
        (map.payType ?: "P") as String,
        (map.payRate ?: 0L) as BigDecimal,
        (map.payMaxMoney ?: 0L) as BigDecimal,
        (map.cashbackTitle ?: "cashbackTitle") as String
    )
  }

  static def RewardBackendCashbackPolicy_생성(Map map = [:]) {
    new RewardBackendCashbackPolicy(
        (map.policyKey ?: 1L) as long,
        (map.cashbackSeq ?: 1) as Integer,
        (map.cashbackCode ?: CashbackType.Item) as CashbackType,
        (map.chargePayRewardRate ?: 0L) as BigDecimal,
        (map.chargePayRewardClubRate ?: 0L) as BigDecimal,
        (map.chargePayRewardMaxMoney ?: 0) as Integer,
        (map.chargePayRewardClubMaxMoney ?: 0) as Integer,
        (map.clubDayPayRate ?: 0L) as BigDecimal,
        (map.clubDaySaveMaxMoney ?: 0) as Integer
    )
  }
}
