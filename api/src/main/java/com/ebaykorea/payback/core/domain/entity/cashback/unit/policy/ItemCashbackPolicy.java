package com.ebaykorea.payback.core.domain.entity.cashback.unit.policy;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemCashbackPolicy extends CashbackPolicy {

  public ItemCashbackPolicy(
      final long policyNo,
      final String name,
      final String subType,
      final String payType,
      final BigDecimal saveRate,
      final BigDecimal maxLimitMoney) {
    super(
        policyNo,
        name,
        subType,
        payType,
        saveRate,
        maxLimitMoney
    );
  }

  @Override
  public CashbackType getCashbackType() {
    return CashbackType.Item;
  }
}
