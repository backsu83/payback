package com.ebaykorea.payback.core.domain.entity.cashback.policy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClubdayPolicy extends PayPolicy {

  public ClubdayPolicy(
      final BigDecimal rate,
      final BigDecimal amount
  ) {
    super(rate, amount);
  }
}
