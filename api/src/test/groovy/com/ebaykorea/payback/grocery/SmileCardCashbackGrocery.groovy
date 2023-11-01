package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.ShopType
import com.ebaykorea.payback.core.domain.constant.SmileCardType
import com.ebaykorea.payback.core.domain.entity.cashback.smilecard.SmileCardCashback
import com.ebaykorea.payback.core.domain.entity.cashback.smilecard.T2SmileCardCashback

class SmileCardCashbackGrocery {
  static def SmileCardCashback_생성(Map map = [:]) {
    SmileCardCashback.of(
        (map.cashbackAmount ?: 0L) as BigDecimal,
        (map.isSmileCard ?: false) as boolean,
        (map.isFreeInstallment ?: false) as boolean,
        (map.t2Cashbacks ?: []) as List<T2SmileCardCashback>
    )
  }
  static def T2SmileCardCashback_생성(Map map = [:]) {
    T2SmileCardCashback.of(
        (map.orderNo ?: 1L) as long,
        (map.shopType ?: ShopType.Unknown) as ShopType,
        (map.amount ?: 0L) as BigDecimal,
        (map.basisAmount ?: 1000L) as BigDecimal,
        (map.smileCardType ?: SmileCardType.Unknown) as SmileCardType,
        (map.isT2 ?: false) as boolean,
        (map.isFreeInstallment ?: false) as boolean
    )
  }

}
