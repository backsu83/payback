package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper;

import com.ebaykorea.payback.core.domain.entity.cashback.Cashback;
import com.ebaykorea.payback.core.domain.entity.cashback.PayCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.CashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.ChargePayCashbackPolicy;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.CashbackOrderPolicyEntity;
import com.ebaykorea.payback.util.PaybackTimestamps;
import com.ebaykorea.payback.util.support.Precondition;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = PaybackTimestamps.class
)
@Precondition(ChargePayCashbackPolicy.class)
public abstract class ChargePayPolicyEntityMapper implements CashbackPolicyEntityMapper {

  @Override
  public CashbackOrderPolicyEntity map(final PayCashback payCashback, final Cashback cashback, final CashbackPolicy policy) {
    return mapToChargePayPolicy(payCashback, cashback, (ChargePayCashbackPolicy) policy);
  }

  @Mapping(source = "policy.cashbackType.dbCode", target = "type")
  @Mapping(source = "payCashback.member.buyerNo", target = "regId")
  @Mapping(expression = "java(PaybackTimestamps.from(payCashback.getOrderDate()))", target = "regDt")
  @Mapping(constant = "0", target = "clubDayMaxSaveRate")
  @Mapping(constant = "0", target = "clubDayMaxSaveMoney")
  abstract CashbackOrderPolicyEntity mapToChargePayPolicy(PayCashback payCashback, Cashback cashback, ChargePayCashbackPolicy policy);
}
