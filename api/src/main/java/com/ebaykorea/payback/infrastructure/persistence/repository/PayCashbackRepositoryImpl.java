package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.core.domain.entity.cashback.Cashback;
import com.ebaykorea.payback.core.domain.entity.cashback.PayCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.CashbackUnit;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.CashbackPolicy;
import com.ebaykorea.payback.core.repository.PayCashbackRepository;
import com.ebaykorea.payback.infrastructure.persistence.mapper.*;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.*;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderPolicyEntity;
import com.ebaykorea.payback.util.support.Conditioner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayCashbackRepositoryImpl implements PayCashbackRepository {

  private final CashbackOrderRepository cashbackOrderRepository;
  private final CashbackOrderPolicyRepository cashbackOrderPolicyRepository;
  private final CashbackOrderDetailRepository cashbackOrderDetailRepository;
  private final CashbackOrderMemberRepository cashbackOrderMemberRepository;

  private final CashbackOrderEntityMapper cashbackOrderEntityMapper;
  private final Conditioner<CashbackPolicyEntityMapper> cashbackPolicyEntityMapperConditioner;
  private final CashbackOrderDetailEntityMapper cashbackOrderDetailEntityMapper;
  private final CashbackOrderMemberEntityMapper cashbackOrderMemberEntityMapper;

  @Transactional
  @Override
  public void save(final PayCashback payCashback) {
    payCashback.getCashbacks()
        .forEach(cashback -> {
          //cashback_order
          saveCashbackUnits(payCashback, cashback, cashback.findAppliedCashbackUnits());

          //cashback_order_policy
          saveCashbackPolicies(payCashback, cashback, cashback.findAppliedCashbackPolicies());

          //cashback_order_detail
          saveCashbackDetail(payCashback, cashback);
        });

    //cashback_order_member
    saveCashbackMember(payCashback);

    //TODO smilecard_cashback

  }

  private void saveCashbackUnits(final PayCashback payCashback, final Cashback cashback, final List<CashbackUnit> cashbackUnits) {
    final var cashbackOrderEntities = cashbackOrderEntityMapper.map(payCashback, cashback, cashbackUnits);
    cashbackOrderEntities.forEach(cashbackOrderRepository::save);
  }

  private void saveCashbackPolicies(final PayCashback payCashback, final Cashback cashback, final List<CashbackPolicy> cashbackPolicies) {
    final var cashbackPolicyEntities = mapToPolicies(payCashback, cashback, cashbackPolicies);
    cashbackPolicyEntities.forEach(cashbackOrderPolicyRepository::save);
  }

  List<CashbackOrderPolicyEntity> mapToPolicies(final PayCashback payCashback, final Cashback cashback, final List<CashbackPolicy> cashbackPolicies) {
    return cashbackPolicies.stream()
        .map(policy -> {
          //cashbackPolicy type(@Precondition)에 맞는 매퍼를 가져 옵니다
          final var policyEntityMapper = cashbackPolicyEntityMapperConditioner.get(policy);
          return policyEntityMapper.map(payCashback, cashback, policy);
        })
        .collect(Collectors.toUnmodifiableList());
  }

  private void saveCashbackDetail(final PayCashback payCashback, final Cashback cashback) {
    final var cashbackDetailEntity = cashbackOrderDetailEntityMapper.map(payCashback, cashback);
    cashbackOrderDetailRepository.save(cashbackDetailEntity);
  }

  private void saveCashbackMember(final PayCashback payCashback) {
    final var cashbackOrderMemberEntity = cashbackOrderMemberEntityMapper.map(payCashback);
    cashbackOrderMemberRepository.save(cashbackOrderMemberEntity);
  }
}
