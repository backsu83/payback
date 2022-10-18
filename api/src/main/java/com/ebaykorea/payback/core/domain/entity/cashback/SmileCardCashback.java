package com.ebaykorea.payback.core.domain.entity.cashback;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import com.ebaykorea.payback.core.domain.constant.ShopType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SmileCardCashback extends Cashback {

  private final BigDecimal t2t3Cashback;
  private final boolean t2t3CashbackApply;

  public SmileCardCashback(
      final long orderNo,
      final String itemNo,
      final CashbackType type,
      final ShopType shopType,
      final BigDecimal amount,
      final BigDecimal basisAmount,
      final Instant useEnableDate,
      final BigDecimal t2t3Cashback,
      final boolean t2t3CashbackApply
  ) {
    super(
        orderNo,
        itemNo,
        type,
        shopType,
        amount,
        basisAmount,
        useEnableDate,
        CashbackApplyStrategy.smileCardCashbackStrategy());
    this.t2t3Cashback = t2t3Cashback;
    this.t2t3CashbackApply = t2t3CashbackApply;
  }
}
