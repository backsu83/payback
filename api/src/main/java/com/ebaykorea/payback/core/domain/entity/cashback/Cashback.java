package com.ebaykorea.payback.core.domain.entity.cashback;

import com.ebaykorea.payback.core.domain.constant.ShopType;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public abstract class Cashback {
  private final long orderNo;
  private final String itemNo;
  private final ShopType shopType;
  private final BigDecimal amount;
  private final BigDecimal basisAmount;
  private final Instant useEnableDate;

  private final CashbackApplyStrategy cashbackApplyStrategy;

  public boolean isApply() {
    return this.cashbackApplyStrategy.isApply();
  }
}
