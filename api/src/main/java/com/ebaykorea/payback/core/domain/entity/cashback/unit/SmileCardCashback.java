package com.ebaykorea.payback.core.domain.entity.cashback.unit;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import com.ebaykorea.payback.core.domain.constant.ShopType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class SmileCardCashback {

  private final String itemNo;
  private final CashbackType type;
  private final ShopType shopType;
  private final BigDecimal amount;
  private final BigDecimal basisAmount;
  private final Instant useEnableDate;
  private final BigDecimal t2t3Cashback;
  private final boolean t2t3CashbackApply;
  private final String smileCardType;
}
