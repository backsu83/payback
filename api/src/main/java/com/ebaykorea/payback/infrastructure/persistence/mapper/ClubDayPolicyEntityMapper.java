package com.ebaykorea.payback.infrastructure.persistence.mapper;

import com.ebaykorea.payback.core.domain.entity.cashback.Cashback;
import com.ebaykorea.payback.core.domain.entity.cashback.PayCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.CashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.ClubDayCashbackPolicy;
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
@Precondition(ClubDayCashbackPolicy.class)
public abstract class ClubDayPolicyEntityMapper implements CashbackPolicyEntityMapper {

  @Override
  public CashbackOrderPolicyEntity map(final PayCashback payCashback, final Cashback cashback, final CashbackPolicy policy) {
    return mapToClubDayPolicy(payCashback, cashback, (ClubDayCashbackPolicy) policy);
  }

  @Mapping(source = "policy.cashbackType.dbCode", target = "type")
  @Mapping(source = "payCashback.member.buyerNo", target = "regId")
  @Mapping(expression = "java(PaybackTimestamps.from(payCashback.getOrderDate()))", target = "regDt")
  @Mapping(constant = "0", target = "chargePaySaveRate")
  @Mapping(constant = "0", target = "chargePayClubSaveRate")
  @Mapping(constant = "0", target = "chargePayMaxMoney")
  @Mapping(constant = "0", target = "chargePayClubMaxMoney")
  abstract CashbackOrderPolicyEntity mapToClubDayPolicy(PayCashback payCashback, Cashback cashback, ClubDayCashbackPolicy policy);
}
