package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.CashbackInfoDto
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.CashbackRewardGoodResponseDto
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.CashbackRewardRequestDto
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.CashbackRewardResponseDto
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.ClubDayCashbackInfoDto
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.ItemCashbackInfoDto
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.NspCashbackInfoDto

class RewardApiGrocery {
  static def CashbackRequestDataDto_생성(Map map = [:]) {
    new CashbackRewardRequestDto().tap {
      totalPrice = (map.totalPrice ?: 1000) as Integer
      goods = (map.goods ?: [CashbackRequestDtoGoods_생성()]) as List<CashbackRewardRequestDto.Goods>
    }
  }

  static def CashbackRequestDtoGoods_생성(Map map = [:]) {
    new CashbackRewardRequestDto.Goods().tap {
      key = (map.key ?: "orderNo1") as String
      siteCd = (map.siteCd ?: 0) as Integer
      gdNo = (map.gdNo ?: "gdNo") as String
      gdlcCd = (map.gdlcCd ?: "gdlcCd") as String
      gdmcCd = (map.gdmcCd ?: "gdmcCd") as String
      gdscCd = (map.gdscCd ?: "gdscCd") as String
      scNo = (map.scNo ?: "scNo") as String
      isSmileClub = (map.isSmileClub ?: false) as Boolean
      isSmileDelivery = (map.isSmileDelivery ?: false) as Boolean
      isSmileFresh = (map.isSmileFresh ?: false) as Boolean
      qty = (map.qty ?: 1) as Integer
      price = (map.price ?: 1000) as Integer
      marketabilityItemYn = (map.marketabilityItemYn ?: "N") as String
    }
  }

  static def CashbackResponseDataDto_생성(Map map = [:]) {
    new CashbackRewardResponseDto().tap {
      totalItemCashbackAmount = (map.totalItemCashbackAmount ?: 0) as Integer
      totalNSPCashbackAmount = (map.totalNSPCashbackAmount ?: 0) as Integer
      ifSmileCardCashbackAmount = (map.ifSmileCardCashbackAmount ?: 0) as Integer
      ifNewSmileCardCashbackAmount = (map.ifNewSmileCardCashbackAmount ?: 0) as Integer
      useEnableDate = (map.useEnableDate ?: "") as String
      goods = (map.goods ?: [CashbackRewardGoodResponseDto_생성()]) as List<CashbackRewardGoodResponseDto>
    }
  }

  static def CashbackRewardGoodResponseDto_생성(Map map = [:]) {
    new CashbackRewardGoodResponseDto().tap {
      clubDayExpectSaveAmount = (map.clubDayExpectSaveAmount ?: 0) as Integer
      clubDayExpectSaveRate = (map.clubDayExpectSaveRate ?: 0) as Integer
      key = (map.key ?: "key") as String
      gdNo = (map.gdNo ?: "gdNo") as String
      ifSmileClubCashbackAmount = (map.ifSmileClubCashbackAmount ?: 0) as Integer
      cashbackInfo = (map.cashbackInfo ?: null) as List<CashbackInfoDto>
      itemCashbackInfo = (map.itemCashbackInfo ?: null) as ItemCashbackInfoDto
      NSPCashbackInfo = (map.NSPCashbackInfo ?: null) as NspCashbackInfoDto
      ifSmileCardT2T3CashbackAmount = (map.ifSmileCardT2T3CashbackAmount ?: 0) as Integer
      clubDayCashbackInfo = (map.clubDayCashbackInfo ?: null) as ClubDayCashbackInfoDto
    }
  }
}
