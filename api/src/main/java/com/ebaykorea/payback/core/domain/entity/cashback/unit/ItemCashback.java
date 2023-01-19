package com.ebaykorea.payback.core.domain.entity.cashback.unit;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import com.ebaykorea.payback.core.domain.constant.ShopType;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.CashbackPolicy;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemCashback extends CashbackUnit {

  public ItemCashback(
      final String itemNo,
      final ShopType shopType,
      final BigDecimal amount,
      final BigDecimal basisAmount,
      final Instant useEnableDate,
      final Boolean isSmilePay,
      final List<CashbackPolicy> cashbackPolicies
      ) {
    super(
        itemNo,
        shopType,
        amount,
        basisAmount,
        useEnableDate,
        CashbackApplyStrategy.cashbackAvailableStrategy(amount, isSmilePay),
        cashbackPolicies);
  }

  @Override
  public CashbackType getCashbackType() {
    return CashbackType.Item;
  }
}
