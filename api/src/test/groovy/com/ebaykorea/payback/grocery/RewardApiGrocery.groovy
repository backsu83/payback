package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.infrastructure.gateway.client.dto.CashbackInfoDto
import com.ebaykorea.payback.infrastructure.gateway.client.dto.CashbackRewardRequestDto
import com.ebaykorea.payback.infrastructure.gateway.client.dto.CashbackRewardResponseDto
import com.ebaykorea.payback.infrastructure.gateway.client.dto.ClubDayCashbackInfoDto
import com.ebaykorea.payback.infrastructure.gateway.client.dto.ItemCashbackInfoDto
import com.ebaykorea.payback.infrastructure.gateway.client.dto.NspCashbackInfoDto

class RewardApiGrocery {
  static def CashbackRequestDataDto_생성(Map map = [:]) {
    new CashbackRewardRequestDto(
        (map.totalPrice ?: 1000) as Integer,
        (map.goods ?: [CashbackRequestDtoGoods_생성()]) as List<CashbackRewardRequestDto.Goods>
    )
  }

  static def CashbackRequestDtoGoods_생성(Map map = [:]) {
    new CashbackRewardRequestDto.Goods(
        (map.siteCd ?: 0) as Integer,
        (map.gdNo ?: "gdNo") as String,
        (map.gdlcCd ?: "gdlcCd") as String,
        (map.gdmcCd ?: "gdmcCd") as String,
        (map.gdscCd ?: "gdscCd") as String,
        (map.scNo ?: "scNo") as String,
        (map.isSmileClub ?: false) as Boolean,
        (map.isSmileDelivery ?: false) as Boolean,
        (map.qty ?: 1) as Integer,
        (map.price ?: 1000) as Integer
    )
  }

  static def CashbackResponseDataDto_생성(Map map = [:]) {
    new CashbackRewardResponseDto(
        (map.totalItemCashbackAmount ?: 0) as Integer,
        (map.totalNSPCashbackAmount ?: 0) as Integer,
        (map.ifSmileCardCashbackAmount ?: 0) as Integer,
        (map.ifNewSmileCardCashbackAmount ?: 0) as Integer,
        (map.useEnableDate ?: "") as String,
        (map.goods ?: [CashbackResponseDtoGoods_생성()]) as List<CashbackRewardResponseDto.Goods>
    )
  }

  static def CashbackResponseDtoGoods_생성(Map map = [:]) {
    new CashbackRewardResponseDto.Goods(
        (map.clubDayExpectSaveAmount ?: 0) as Integer,
        (map.clubDayExpectSaveRate ?: 0) as Integer,
        (map.key ?: "key") as String,
        (map.gdNo ?: "gdNo") as String,
        (map.ifSmileClubCashbackAmount ?: 0) as Integer,
        (map.cashbackInfo ?: null) as List<CashbackInfoDto>,
        (map.itemCashbackInfo ?: null) as ItemCashbackInfoDto,
        (map.NSPCashbackInfo ?: null) as NspCashbackInfoDto,
        (map.ifSmileCardT2T3CashbackAmount ?: 0) as Integer,
        (map.clubDayCashbackInfo ?: null) as ClubDayCashbackInfoDto,
    )
  }
}
