package com.ebaykorea.payback.core.domain.entity.cashback;

import java.math.BigDecimal;
import static com.ebaykorea.payback.util.PaybackDecimals.isGreaterThanZero;

@FunctionalInterface
public interface CashbackApplyStrategy {
  boolean isApply();

  static CashbackApplyStrategy defaultCashbackStrategy(final BigDecimal amount) {
    return () -> isGreaterThanZero(amount);
  }

  //TODO: cashbackAvailable 조건 확인
  static CashbackApplyStrategy cashbackAvailableStrategy(final BigDecimal amount, final boolean cashbackAvailable) {
    return () -> isGreaterThanZero(amount) && cashbackAvailable;
  }

  static CashbackApplyStrategy chargePayCashbackStrategy(final BigDecimal amount, final boolean isChargePay) {
    return () -> isGreaterThanZero(amount) && isChargePay;
  }

  static CashbackApplyStrategy clubDayCashbackStrategy(final BigDecimal amount, final boolean isClubMember) {
    return () -> isGreaterThanZero(amount) && isClubMember;
  }

  //cashback_order 저장 대상이 아님
  static CashbackApplyStrategy smileCardCashbackStrategy() {
    return () -> false;
  }
}
