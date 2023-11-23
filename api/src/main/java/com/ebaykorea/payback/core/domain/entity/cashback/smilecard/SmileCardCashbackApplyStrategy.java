package com.ebaykorea.payback.core.domain.entity.cashback.smilecard;

import com.ebaykorea.payback.core.domain.constant.SmileCardType;

import java.math.BigDecimal;

import static com.ebaykorea.payback.util.PaybackDecimals.isGreaterThanZero;

@FunctionalInterface
public interface SmileCardCashbackApplyStrategy {
  boolean isApply();

  static SmileCardCashbackApplyStrategy defaultSmileCardCashbackStrategy(final SmileCardType smileCardType, final boolean isFreeInstallment, final BigDecimal amount) {
    return () -> smileCardType != SmileCardType.Unknown && !isFreeInstallment && isGreaterThanZero(amount);
  }

  static SmileCardCashbackApplyStrategy additionalSmileCardCashbackStrategy(final SmileCardType smileCardType, final boolean isFreeInstallment, final BigDecimal amount) {
    return () -> smileCardType == SmileCardType.T2 && !isFreeInstallment && isGreaterThanZero(amount);
  }
}
