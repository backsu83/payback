package com.ebaykorea.payback.core.domain.entity.cashback.smilecard;

import com.ebaykorea.payback.core.domain.constant.ShopType;
import com.ebaykorea.payback.core.domain.constant.SmileCardType;
import lombok.*;

import java.math.BigDecimal;

@Value
public class T2T3SmileCardCashback {
  long orderNo;
  ShopType shopType;
  BigDecimal amount;
  BigDecimal basisAmount;
  SmileCardType smileCardType;
  SmileCardCashbackApplyStrategy strategy;

  public static T2T3SmileCardCashback of(
      final long orderNo,
      final ShopType shopType,
      final BigDecimal amount,
      final BigDecimal basisAmount,
      final SmileCardType smileCardType,
      final boolean isT2T3,
      final boolean isFreeInstallment) {
    return new T2T3SmileCardCashback(
        orderNo,
        shopType,
        amount,
        basisAmount,
        smileCardType,
        SmileCardCashbackApplyStrategy.t2t3CashbackStrategy(isT2T3, isFreeInstallment, amount));
  }

  private T2T3SmileCardCashback(
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
}
