package com.ebaykorea.payback.core.domain.entity.cashback;

import com.ebaykorea.payback.core.domain.entity.cashback.unit.CashbackUnit;
import lombok.*;

import java.util.List;

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
}
