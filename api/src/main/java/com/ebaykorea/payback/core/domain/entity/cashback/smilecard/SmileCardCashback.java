package com.ebaykorea.payback.core.domain.entity.cashback.smilecard;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class SmileCardCashback {

  BigDecimal cashbackAmount;
  SmileCardCashbackApplyStrategy strategy;

  List<T2T3SmileCardCashback> t2t3Cashbacks;

  public static SmileCardCashback of(
      final BigDecimal cashbackAmount,
      final boolean isSmileCard,
      final boolean isFreeInstallment,
      final List<T2T3SmileCardCashback> t2t3Cashbacks) {
    return new SmileCardCashback(
        cashbackAmount,
        SmileCardCashbackApplyStrategy.defaultSmileCardCashbackStrategy(isSmileCard, isFreeInstallment, cashbackAmount),
        t2t3Cashbacks);
  }

  private SmileCardCashback(
      final BigDecimal cashbackAmount,
      final SmileCardCashbackApplyStrategy strategy,
      final List<T2T3SmileCardCashback> t2t3Cashbacks) {
    this.cashbackAmount = cashbackAmount;
    this.strategy = strategy;
    this.t2t3Cashbacks = t2t3Cashbacks;
  }

  public boolean isApply() {
    return strategy.isApply();
  }
}
