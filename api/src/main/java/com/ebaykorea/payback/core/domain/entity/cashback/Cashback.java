package com.ebaykorea.payback.core.domain.entity.cashback;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.CashbackUnit;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.CashbackPolicy;
import com.ebaykorea.payback.core.exception.PaybackException;
import lombok.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_001;
import static com.ebaykorea.payback.util.PaybackDecimals.summarizing;
import static java.util.stream.Collectors.groupingBy;

@Getter
@EqualsAndHashCode
@ToString
public class Cashback {
  private final long orderNo;
  /* 주문 단위 별 캐시백 목록 */
  private final List<CashbackUnit> cashbackUnits;

  public static Cashback of(
      final long orderNo,
      final List<CashbackUnit> cashbackUnits) {
    return new Cashback(orderNo, cashbackUnits);
  }

  private Cashback(
      final long orderNo,
      final List<CashbackUnit> cashbackUnits
  ) {
    this.orderNo = orderNo;
    this.cashbackUnits = cashbackUnits;

    validate();
  }

  private void validate() {
    //cashbackUnits에 동일한 캐시백 타입이 두개 이상 존재 할 수 없다
    final var hasMoreThanOneCashbackType = cashbackUnits.stream().collect(groupingBy(CashbackUnit::getCashbackType))
        .entrySet().stream()
        .anyMatch(entry -> entry.getValue().size() > 1);
    if (hasMoreThanOneCashbackType) {
      throw new PaybackException(DOMAIN_ENTITY_001 , "Cashback");
    }
  }

  // 캐시백 적립 대상(isApply=true) 목록
  public List<CashbackUnit> findAppliedCashbackUnits() {
    return cashbackUnits.stream()
        .filter(CashbackUnit::isApply)
        .collect(Collectors.toUnmodifiableList());
  }

  // 캐시백 정책 목록
  public List<CashbackPolicy> findCashbackPolicies() {
    return cashbackUnits.stream()
        .map(CashbackUnit::getCashbackPolicies)
        .flatMap(Collection::stream)
        .collect(Collectors.toUnmodifiableList());
  }

  //전체 cashbackUnit 대상중 해당하는 캐시백 타입 대상건
  private Stream<CashbackUnit> cashbackUnitStreamByCashbackType(final CashbackType cashbackType) {
    return cashbackUnits.stream()
        .filter(cashbackUnit -> cashbackUnit.is(cashbackType));
  }

  public BigDecimal getCashbackAmount(final CashbackType cashbackType) {
    return cashbackUnitStreamByCashbackType(cashbackType)
        .map(CashbackUnit::getAmount)
        .collect(summarizing());
  }

  public BigDecimal getClubAmount(final CashbackType cashbackType) {
    return cashbackUnitStreamByCashbackType(cashbackType)
        .map(CashbackUnit::getClubAmount)
        .collect(summarizing());
  }

  public BigDecimal getNonClubAmount(final CashbackType cashbackType) {
    return cashbackUnitStreamByCashbackType(cashbackType)
        .map(CashbackUnit::getNonClubAmount)
        .collect(summarizing());
  }

  public boolean isApplyBy(final CashbackType cashbackType) {
    return cashbackUnitStreamByCashbackType(cashbackType)
        .map(CashbackUnit::isApply)
        .findAny()
        .orElse(false);
  }

}
