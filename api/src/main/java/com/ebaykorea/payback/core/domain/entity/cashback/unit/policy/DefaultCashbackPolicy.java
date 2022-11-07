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

  public DefaultCashbackPolicy(
      final long policyNo,
      final CashbackType type,
      final String name,
      final String subType,
      final String payType,
      final BigDecimal rate,
      final BigDecimal cashbackMaxLimitMoney
  ) {
    super(
        policyNo,
        type,
        name,
        subType,
        payType,
        rate,
        cashbackMaxLimitMoney
    );
  }
}
