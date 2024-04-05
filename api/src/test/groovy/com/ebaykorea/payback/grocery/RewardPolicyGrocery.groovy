package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.CashbackType
import com.ebaykorea.payback.core.domain.entity.reward.RewardPolicy
import com.ebaykorea.payback.core.domain.entity.reward.RewardPolicyDetail
import com.ebaykorea.payback.core.domain.entity.reward.RewardPolicyGroup
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardPolicyDetailDto
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardPolicyDetailRequestDto
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardPolicyDto
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardPolicyGroupDto
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardPolicyRequestDto

class RewardPolicyGrocery {

  static def RewardPolicyRequestDto_생성(Map map = [:]) {
    new RewardPolicyRequestDto().tap {
      totalPrice = (map.totalPrice ?: 0) as BigDecimal
      club = (map.club ?: false) as boolean
      details = (map.details ?: []) as List<RewardPolicyDetailRequestDto>
    }
  }

  static def RewardPolicyDetailRequestDto_생성(Map map = [:]) {
    new RewardPolicyDetailRequestDto().tap {
      key = (map.key ?: "1") as String
      itemNo = (map.itemNo ?: "itemNo1") as String
      largeCategoryCode = (map.largeCategoryCode ?: "1") as String
      mediumCategoryCode = (map.mediumCategoryCode ?: "2") as String
      smallCategoryCode = (map.smallCategoryCode ?: "3") as String
      sellerKey = (map.sellerKey ?: "sellerCustNo") as String
      itemType = (map.itemType ?: "") as String
      qty = (map.qty ?: 1) as int
      price = (map.price ?: 0) as BigDecimal
      redeemable = (map.redeemable ?: false) as boolean
    }
  }

  static def RewardPolicyDto_생성(Map map = [:]) {
    new RewardPolicyDto().tap {
      policyDetails = (map.policyDetails ?: [RewardPolicyDetailDto_생성(map)]) as List<RewardPolicyDetailDto>
      t0SmileCardCashbackAmount = (map.t0SmileCardCashbackAmount ?: 0) as BigDecimal
      t1t2SmileCardCashbackAmount = (map.t1t2SmileCardCashbackAmount ?: 0) as BigDecimal
      t3SmileCardCashbackAmount = (map.t3SmileCardCashbackAmount ?: 0) as BigDecimal
    }
  }

  static def RewardPolicyDetailDto_생성(Map map = [:]) {
    new RewardPolicyDetailDto().tap {
      key = (map.key ?: "1") as String
      smileCardAdditionalCashbackAmount = (map.smileCardAdditionalCashbackAmount ?: 0) as BigDecimal
      policyGroups = (map.policyGroups ?: [RewardPolicyGroupDto_생성(map)]) as List<RewardPolicyGroupDto>
    }
  }

  static def RewardPolicyGroupDto_생성(Map map = [:]) {
    new RewardPolicyGroupDto().tap {
      policyNo = (map.policyNo ?: 1) as long
      type = (map.type ?: null) as String
      title = (map.title ?: "title") as String
      payType = (map.payType ?: "P") as String
      subType = (map.subType ?: "subType") as String
      appliedSaveAmount = (map.appliedSaveAmount ?: 0) as BigDecimal
      nonClubSaveAmount = (map.nonClubSaveAmount ?: 0) as BigDecimal
      clubSaveAmount = (map.clubSaveAmount ?: 0) as BigDecimal
      appliedSaveRate = (map.appliedSaveRate ?: 0) as BigDecimal
      nonClubSaveRate = (map.nonClubSaveRate ?: 0) as BigDecimal
      clubSaveRate = (map.clubSaveRate ?: 0) as BigDecimal
      nonClubMaxSaveAmount = (map.nonClubMaxSaveAmount ?: 0) as BigDecimal
      clubMaxSaveAmount = (map.clubMaxSaveAmount ?: 0) as BigDecimal
    }
  }

  static def RewardPolicy_생성(Map map = [:]) {
    new RewardPolicy().tap {
      policyDetails = (map.policyDetails ?: [RewardPolicyDetail_생성(map)]) as List<RewardPolicyDetail>
      t0SmileCardCashbackAmount = (map.t0SmileCardCashbackAmount ?: 0) as BigDecimal
      t1t2SmileCardCashbackAmount = (map.t1t2SmileCardCashbackAmount ?: 0) as BigDecimal
      t3SmileCardCashbackAmount = (map.t3SmileCardCashbackAmount ?: 0) as BigDecimal
    }
  }

  static def RewardPolicyDetail_생성(Map map = [:]) {
    new RewardPolicyDetail().tap {
      key = (map.key ?: "1") as String
      smileCardAdditionalCashbackAmount = (map.smileCardAdditionalCashbackAmount ?: 0) as BigDecimal
      policyGroups = (map.policyGroups ?: [RewardPolicyGroup_생성(map)]) as List<RewardPolicyGroup>
    }
  }

  static def RewardPolicyGroup_생성(Map map = [:]) {
    new RewardPolicyGroup().tap {
      policyNo = (map.policyNo ?: 1) as long
      type = (map.type ?: CashbackType.Unknown) as CashbackType
      title = (map.title ?: "title") as String
      payType = (map.payType ?: "P") as String
      subType = (map.subType ?: "subType") as String
      appliedSaveAmount = (map.appliedSaveAmount ?: 0) as BigDecimal
      nonClubSaveAmount = (map.nonClubSaveAmount ?: 0) as BigDecimal
      clubSaveAmount = (map.clubSaveAmount ?: 0) as BigDecimal
      appliedSaveRate = (map.appliedSaveRate ?: 0) as BigDecimal
      nonClubSaveRate = (map.nonClubSaveRate ?: 0) as BigDecimal
      clubSaveRate = (map.clubSaveRate ?: 0) as BigDecimal
      nonClubMaxSaveAmount = (map.nonClubMaxSaveAmount ?: 0) as BigDecimal
      clubMaxSaveAmount = (map.clubMaxSaveAmount ?: 0) as BigDecimal
    }
  }
}
