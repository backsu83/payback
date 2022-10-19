package com.ebaykorea.payback.core.domain.entity.cashback.policy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChargePayPolicy extends PayPolicy {
  private final BigDecimal clubRate;
  private final BigDecimal clubAmount;

  public ChargePayPolicy(
      final BigDecimal rate,
      final BigDecimal amount,
      final BigDecimal clubRate,
      final BigDecimal clubAmount) {
    super(rate, amount);
    this.clubRate = clubRate;
    this.clubAmount = clubAmount;
  }
}
