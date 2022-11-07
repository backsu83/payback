package com.ebaykorea.payback.core.domain.entity.cashback.unit;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import com.ebaykorea.payback.core.domain.constant.ShopType;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.CashbackPolicy;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public abstract class CashbackUnit {
  //private final long cashbackUnitId;
  private final String itemNo;
  private final CashbackType type; //TODO: type은 다른 방법으로 처리해도 될거같다
  private final ShopType shopType;
  private final BigDecimal amount;
  private final BigDecimal basisAmount;
  private final Instant useEnableDate;
  private final CashbackApplyStrategy cashbackApplyStrategy;
  private final CashbackPolicy cashbackPolicy;

  //캐시백 적용여부
  public boolean isApply() {
    return this.cashbackApplyStrategy.isApply();
  }
}
