package com.ebaykorea.payback.core.domain.entity.cashback.smilecard;

import static com.ebaykorea.payback.util.PaybackDecimals.isGreaterThanZero;
import static com.ebaykorea.payback.util.PaybackDecimals.summarizing;

import com.ebaykorea.payback.core.domain.constant.ShopType;
import com.ebaykorea.payback.core.domain.constant.SmileCardType;
import com.ebaykorea.payback.util.PaybackDecimals;
import com.ebaykorea.payback.util.PaybackNumbers;
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
  List<T2SmileCardCashback> t2Cashbacks;

  public static SmileCardCashback of(
      final BigDecimal cashbackAmount,
      final boolean isSmileCard,
      final boolean isFreeInstallment,
      final List<T2SmileCardCashback> t2Cashbacks) {
    return new SmileCardCashback(
        cashbackAmount,
        SmileCardCashbackApplyStrategy.defaultSmileCardCashbackStrategy(isSmileCard, isFreeInstallment, cashbackAmount),
        t2Cashbacks);
  }

  private SmileCardCashback(
      final BigDecimal cashbackAmount,
      final SmileCardCashbackApplyStrategy strategy,
      final List<T2SmileCardCashback> t2Cashbacks) {
    this.cashbackAmount = cashbackAmount;
    this.strategy = strategy;
    this.t2Cashbacks = t2Cashbacks;
  }

  public BigDecimal sumT2Amount() {
    return t2Cashbacks
        .stream()
        .map(T2SmileCardCashback::getAmount)
        .collect(summarizing());
  }

  public ShopType toShopType() {
    boolean isSmileDelivery = t2Cashbacks.stream().anyMatch(T2SmileCardCashback::isSmileDelivery);
    boolean isSmileFresh = t2Cashbacks.stream().anyMatch(T2SmileCardCashback::isSmileFresh);
    return isSmileDelivery ? ShopType.SmileDelivery : isSmileFresh ? ShopType.SmileFresh : ShopType.Unknown;
  }

  public boolean hasSmileCardCashbackAmount() {
    return isGreaterThanZero(cashbackAmount) || isGreaterThanZero(sumT2Amount());
  }

  public boolean isApply() {
    return strategy.isApply();
  }

  public boolean isApplyT2() {
    return t2Cashbacks.stream().anyMatch(T2SmileCardCashback::isApply);
  }
}
