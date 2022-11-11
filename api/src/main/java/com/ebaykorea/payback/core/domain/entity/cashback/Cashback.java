package com.ebaykorea.payback.core.domain.entity.cashback;

import com.ebaykorea.payback.core.domain.entity.cashback.unit.CashbackUnit;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.CashbackPolicy;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Cashback {

  private final String orderUnitKey;

  private final long orderNo;
  /* 주문 단위 별 캐시백 목록 */
  private final List<CashbackUnit> cashbackUnits;

  public static Cashback of(
      final String orderUnitKey,
      final long orderNo,
      final List<CashbackUnit> cashbackUnits) {
    return new Cashback(orderUnitKey, orderNo, cashbackUnits);
  }

  public List<CashbackUnit> findApplyCashbackUnits() {
    return cashbackUnits.stream()
        .filter(CashbackUnit::isApply)
        .collect(Collectors.toUnmodifiableList());
  }

  public List<CashbackPolicy> findApplyCashbackPolicies() {
    return findApplyCashbackUnits().stream()
        .map(CashbackUnit::getCashbackPolicy)
        .collect(Collectors.toUnmodifiableList());
  }
}
