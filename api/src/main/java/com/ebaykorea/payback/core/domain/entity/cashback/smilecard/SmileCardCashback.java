package com.ebaykorea.payback.core.domain.entity.cashback.smilecard;

import static com.ebaykorea.payback.util.PaybackDecimals.isGreaterThanZero;
import static com.ebaykorea.payback.util.PaybackDecimals.summarizing;

import com.ebaykorea.payback.core.domain.constant.ShopType;
import com.ebaykorea.payback.core.domain.constant.SmileCardType;
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
  List<SmileCardAdditionalCashback> additionalCashbacks;
  String smileCardTypeCode;

  public static SmileCardCashback of(
      final BigDecimal cashbackAmount,
      final SmileCardType smileCardType,
      final boolean isFreeInstallment,
      final List<SmileCardAdditionalCashback> additionalCashbacks) {
    return new SmileCardCashback(
        cashbackAmount,
        SmileCardCashbackApplyStrategy.defaultSmileCardCashbackStrategy(smileCardType, isFreeInstallment, cashbackAmount),
        additionalCashbacks,
        smileCardType.getCode());
  }

  private SmileCardCashback(
      final BigDecimal cashbackAmount,
      final SmileCardCashbackApplyStrategy strategy,
      final List<SmileCardAdditionalCashback> additionalCashbacks,
      final String smileCardTypeCode) {
    this.cashbackAmount = cashbackAmount;
    this.strategy = strategy;
    this.additionalCashbacks = additionalCashbacks;
    this.smileCardTypeCode = smileCardTypeCode;
  }

  public BigDecimal sumAdditionalAmount() {
    return additionalCashbacks
        .stream()
        .map(SmileCardAdditionalCashback::getAmount)
        .collect(summarizing());
  }

  public ShopType toShopType() {
    boolean isSmileDelivery = additionalCashbacks.stream().anyMatch(SmileCardAdditionalCashback::isSmileDelivery);
    boolean isSmileFresh = additionalCashbacks.stream().anyMatch(SmileCardAdditionalCashback::isSmileFresh);
    return isSmileDelivery ? ShopType.SmileDelivery : isSmileFresh ? ShopType.SmileFresh : ShopType.Unknown;
  }

  public boolean hasSmileCardCashbackAmount() {
    return isGreaterThanZero(cashbackAmount) || isGreaterThanZero(sumAdditionalAmount());
  }

  public boolean isApply() {
    return strategy.isApply();
  }

  public boolean hasAdditionalCashback() {
    return additionalCashbacks.stream().anyMatch(SmileCardAdditionalCashback::isApply);
  }
}
