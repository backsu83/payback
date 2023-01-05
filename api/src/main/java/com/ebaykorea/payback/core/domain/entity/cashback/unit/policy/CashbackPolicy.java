package com.ebaykorea.payback.core.domain.entity.cashback.unit.policy;

import com.ebaykorea.payback.core.domain.entity.cashback.unit.CashbackUnitKind;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public abstract class CashbackPolicy implements CashbackUnitKind {
  long policyNo;
  String name;
  String subType;
  String payType;
  BigDecimal saveRate;
  BigDecimal maxLimitMoney;
}
