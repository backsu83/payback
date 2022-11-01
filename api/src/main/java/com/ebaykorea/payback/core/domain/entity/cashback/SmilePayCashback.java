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
public class SmilePayCashback extends Cashback {

  public SmilePayCashback(
      final long orderNo,
      final String itemNo,
      final CashbackType type,
      final ShopType shopType,
      final BigDecimal amount, //cashback_order와 detail.PAY_AMOUNT 저장하는 금액 소스가 다른데 통일할수 없는지 확인 필요
      final BigDecimal basisAmount,
      final Instant useEnableDate,
      final boolean cashbackAvailable
  ) {
    super(
        orderNo,
        itemNo,
        type,
        shopType,
        amount,
        basisAmount,
        useEnableDate,
        CashbackApplyStrategy.cashbackAvailableStrategy(amount, cashbackAvailable));
  }
}
