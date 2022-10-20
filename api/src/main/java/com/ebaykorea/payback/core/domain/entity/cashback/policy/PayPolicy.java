package com.ebaykorea.payback.core.domain.entity.cashback.policy;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public abstract class PayPolicy {
  private final BigDecimal rate;
  private final BigDecimal amount;

}
