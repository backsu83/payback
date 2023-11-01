package com.ebaykorea.payback.core.domain.entity.cashback.smilecard;

import java.math.BigDecimal;

import static com.ebaykorea.payback.util.PaybackDecimals.isGreaterThanZero;

@FunctionalInterface
public interface SmileCardCashbackApplyStrategy {
  boolean isApply();

  static SmileCardCashbackApplyStrategy defaultSmileCardCashbackStrategy(final boolean isSmileCard, final boolean isFreeInstallment, final BigDecimal amount) {
    return () -> isSmileCard && !isFreeInstallment && isGreaterThanZero(amount);
  }

  static SmileCardCashbackApplyStrategy t2SmileCardCashbackStrategy(final boolean isT2, final boolean isFreeInstallment, final BigDecimal amount) {
    return () -> isT2 && !isFreeInstallment && isGreaterThanZero(amount);
  }
}
