package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.core.domain.constant.CashbackType
import com.ebaykorea.payback.core.domain.constant.OrderSiteType
import com.ebaykorea.payback.core.domain.constant.ShopType
import com.ebaykorea.payback.core.domain.entity.cashback.Cashback
import com.ebaykorea.payback.core.domain.entity.cashback.ChargePayCashback
import com.ebaykorea.payback.core.domain.entity.cashback.ClubDayCashback
import com.ebaykorea.payback.core.domain.entity.cashback.ItemCashback
import com.ebaykorea.payback.core.domain.entity.cashback.NoneCashback
import com.ebaykorea.payback.core.domain.entity.cashback.PayCashback
import com.ebaykorea.payback.core.domain.entity.cashback.SellerCashback
import com.ebaykorea.payback.core.domain.entity.cashback.SmileCardCashback
import com.ebaykorea.payback.core.domain.entity.cashback.SmilePayCashback
import com.ebaykorea.payback.core.domain.entity.cashback.member.Member
import com.ebaykorea.payback.core.domain.entity.cashback.policy.CashbackPolicy

import java.time.Instant

import static com.ebaykorea.payback.grocery.MemberGrocery.회원_생성

class PayCashbackGrocery {
  static def PayCashback_생성(Map map = [:]) {
    PayCashback.of(
        (map.orderKey ?: "orderKey") as String,
        (map.packNo ?: 1L) as long,
        (map.orderSiteType ?: OrderSiteType.Unknown) as OrderSiteType,
        (map.orderDate ?: TestConstant.ORDER_DATE) as Instant,
        (map.member ?: 회원_생성()) as Member,
        (map.cashbacks ?: []) as List<Cashback>,
        (map.cashbackPolicies ?: []) as List<CashbackPolicy>,
    )
  }

  static def ItemCashback_생성(Map map = [:]) {
    new ItemCashback(
        (map.orderNo ?: 1L) as long,
        (map.itemNo ?: "itemNo1") as String,
        (map.type ?: CashbackType.Item) as CashbackType,
        (map.shopType ?: ShopType.Unknown) as ShopType,
        (map.amount ?: 1000L) as BigDecimal,
        (map.basisAmount ?: 1000L) as BigDecimal,
        (map.useEnableDate ?: TestConstant.USE_ENABLE_DATE) as Instant,
        (map.isSmilePay) as Boolean
    )
  }

  static def SmilePayCashback_생성(Map map = [:]) {
    new SmilePayCashback(
        (map.orderNo ?: 1L) as long,
        (map.itemNo ?: "itemNo1") as String,
        (map.type ?: CashbackType.SmilePay) as CashbackType,
        (map.shopType ?: ShopType.Unknown) as ShopType,
        (map.amount ?: 1000L) as BigDecimal,
        (map.basisAmount ?: 1000L) as BigDecimal,
        (map.useEnableDate ?: TestConstant.USE_ENABLE_DATE) as Instant,
        (map.isSmilePay) as Boolean
    )
  }

  static def ChargePayCashback_생성(Map map = [:]) {
    new ChargePayCashback(
        (map.orderNo ?: 1L) as long,
        (map.itemNo ?: "itemNo1") as String,
        (map.type ?: CashbackType.ChargePay) as CashbackType,
        (map.shopType ?: ShopType.Unknown) as ShopType,
        (map.amount ?: 1000L) as BigDecimal,
        (map.basisAmount ?: 1000L) as BigDecimal,
        (map.useEnableDate ?: TestConstant.USE_ENABLE_DATE) as Instant,
        (map.clubAmount ?: 1000L) as BigDecimal,
        (map.isChargePay) as Boolean
    )
  }

  static def ClubDayCashback_생성(Map map = [:]) {
    new ClubDayCashback(
        (map.orderNo ?: 1L) as long,
        (map.itemNo ?: "itemNo1") as String,
        (map.type ?: CashbackType.ClubDay) as CashbackType,
        (map.shopType ?: ShopType.Unknown) as ShopType,
        (map.amount ?: 1000L) as BigDecimal,
        (map.basisAmount ?: 1000L) as BigDecimal,
        (map.useEnableDate ?: TestConstant.USE_ENABLE_DATE) as Instant,
        (map.isClubMember) as Boolean
    )
  }

  static def NoneCashback_생성(Map map = [:]) {
    new NoneCashback(
        (map.orderNo ?: 1L) as long,
        (map.itemNo ?: "itemNo1") as String,
        (map.type ?: CashbackType.Unknown) as CashbackType,
        (map.shopType ?: ShopType.Unknown) as ShopType,
        (map.amount ?: 1000L) as BigDecimal,
        (map.basisAmount ?: 1000L) as BigDecimal,
        (map.useEnableDate ?: TestConstant.USE_ENABLE_DATE) as Instant
    )
  }

  static def SellerCashback_생성(Map map = [:]) {
    new SellerCashback(
        (map.orderNo ?: 1L) as long,
        (map.itemNo ?: "itemNo1") as String,
        (map.type ?: CashbackType.Seller) as CashbackType,
        (map.shopType ?: ShopType.Unknown) as ShopType,
        (map.amount ?: 0L) as BigDecimal,
        (map.basisAmount ?: 1000L) as BigDecimal,
        (map.useEnableDate ?: TestConstant.ORDER_DATE_ADD_TO_30_DAYS) as Instant
    )
  }

  static def SmileCardCashback_생성(Map map = [:]) {
    new SmileCardCashback(
        (map.orderNo ?: 1L) as long,
        (map.itemNo ?: "itemNo1") as String,
        (map.type ?: CashbackType.Unknown) as CashbackType,
        (map.shopType ?: ShopType.Unknown) as ShopType,
        (map.amount ?: 1000L) as BigDecimal,
        (map.basisAmount ?: 1000L) as BigDecimal,
        (map.useEnableDate ?: TestConstant.USE_ENABLE_DATE) as Instant,
        (map.t2t3Cashback ?: 1000L) as BigDecimal,
        (map.t2t3CashbackApply) as boolean,
        (map.smileCardType ?: "itemNo1") as String
    )
  }

}
