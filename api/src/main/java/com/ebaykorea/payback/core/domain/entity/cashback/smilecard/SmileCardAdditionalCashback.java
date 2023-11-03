package com.ebaykorea.payback.core.domain.entity.cashback.smilecard;

import static com.ebaykorea.payback.core.domain.constant.ShopType.SmileDelivery;
import static com.ebaykorea.payback.core.domain.constant.ShopType.SmileFresh;

import com.ebaykorea.payback.core.domain.constant.ShopType;
import com.ebaykorea.payback.core.domain.constant.SmileCardType;
import lombok.*;

import java.math.BigDecimal;

@Value
public class SmileCardAdditionalCashback {
  long orderNo;
  ShopType shopType;
  BigDecimal amount;
  BigDecimal basisAmount;
  SmileCardType smileCardType;
  SmileCardCashbackApplyStrategy strategy;

  public static SmileCardAdditionalCashback of(
      final long orderNo,
      final ShopType shopType,
      final BigDecimal amount,
      final BigDecimal basisAmount,
      final SmileCardType smileCardType,
      final boolean isFreeInstallment) {
    return new SmileCardAdditionalCashback(
        orderNo,
        shopType,
        amount,
        basisAmount,
        smileCardType,
        SmileCardCashbackApplyStrategy.additionalSmileCardCashbackStrategy(smileCardType, isFreeInstallment, amount));
  }

  private SmileCardAdditionalCashback(
      final long orderNo,
      final ShopType shopType,
      final BigDecimal amount,
      final BigDecimal basisAmount,
      final SmileCardType smileCardType,
      final SmileCardCashbackApplyStrategy strategy) {
    this.orderNo = orderNo;
    this.shopType = shopType;
    this.amount = amount;
    this.basisAmount = basisAmount;
    this.smileCardType = smileCardType;
    this.strategy = strategy;
  }

  public boolean isApply() {
    return strategy.isApply();
  }

  public boolean isSmileDelivery() {
    return shopType == SmileDelivery;
  }
  public boolean isSmileFresh() {
    return shopType == SmileFresh;
  }
}
