package com.ebaykorea.payback.core.domain.entity.cashback.unit.policy;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DefaultCashbackPolicy extends CashbackPolicy {

  private final CashbackType type;

  public DefaultCashbackPolicy(
      final long policyNo,
      final CashbackType type,
      final String name,
      final String subType,
      final String payType,
      final BigDecimal saveRate,
      final BigDecimal maxLimitMoney
  ) {
    super(
        policyNo,
        name,
        subType,
        payType,
        saveRate,
        maxLimitMoney
    );
    this.type = type;
  }

  @Override
  public CashbackType getCashbackType() {
    return this.type;
  }
}
