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
public class NoneCashback extends Cashback {
  private final CashbackType type;

  public NoneCashback(
      final long orderNo,
      final String itemNo,
      final ShopType shopType,
      final BigDecimal amount,
      final BigDecimal basisAmount,
      final Instant useEnableDate,
      final CashbackType type
  ) {
    super(
        orderNo,
        itemNo,
        shopType,
        amount,
        basisAmount,
        useEnableDate,
        CashbackApplyStrategy.notForSaveStrategy());
    this.type = type;
  }
}
