package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.core.domain.constant.OrderSiteType
import com.ebaykorea.payback.core.domain.entity.cashback.Cashback
import com.ebaykorea.payback.core.domain.entity.cashback.PayCashback
import com.ebaykorea.payback.core.domain.entity.cashback.member.Member

import java.time.Instant

import static com.ebaykorea.payback.grocery.MemberGrocery.회원_생성

class PayCashbackGrocery {
  static def PayCashback_생성(Map map = [:]) {
    PayCashback.of(
        (map.txKey ?: "txKey") as String,
        (map.packNo ?: 1L) as long,
        (map.orderSiteType ?: OrderSiteType.Unknown) as OrderSiteType,
        (map.orderDate ?: TestConstant.ORDER_DATE) as Instant,
        (map.member ?: 회원_생성()) as Member,
        (map.cashbacks ?: []) as List<Cashback>
    )
  }

//  TODO
//  static def SmileCardCashback_생성(Map map = [:]) {
//    new SmileCardCashback(
//        (map.orderNo ?: 1L) as long,
//        (map.itemNo ?: "itemNo1") as String,
//        (map.type ?: CashbackType.Unknown) as CashbackType,
//        (map.shopType ?: ShopType.Unknown) as ShopType,
//        (map.amount ?: 1000L) as BigDecimal,
//        (map.basisAmount ?: 1000L) as BigDecimal,
//        (map.useEnableDate ?: TestConstant.USE_ENABLE_DATE) as Instant,
//        (map.t2t3Cashback ?: 1000L) as BigDecimal,
//        (map.t2t3CashbackApply) as boolean,
//        (map.smileCardType ?: "itemNo1") as String
//    )
//  }

}
