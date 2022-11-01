package com.ebaykorea.payback.core.domain.entity.cashback;

import com.ebaykorea.payback.core.domain.constant.ShopType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChargePayCashback extends Cashback {

  private final BigDecimal clubAmount;

  public ChargePayCashback(
      final long orderNo,
      final String itemNo,
      final ShopType shopType,
      final BigDecimal amount, //cashback_order와 detail.CHARGE_PAY_REWARD 저장하는 금액 소스가 다른데 통일할수 없는지 확인 필요
      final BigDecimal basisAmount,
      final Instant useEnableDate,
      final BigDecimal clubAmount,
      final boolean isChargePay
  ) {
    super(
        orderNo,
        itemNo,
        shopType,
        amount,
        basisAmount,
        useEnableDate,
        CashbackApplyStrategy.chargePayCashbackStrategy(amount, isChargePay));

    this.clubAmount = clubAmount;
  }
}
