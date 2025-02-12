package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt;

import com.ebaykorea.payback.core.domain.entity.cashback.Cashback;
import com.ebaykorea.payback.core.domain.entity.cashback.PayCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.smilecard.SmileCardCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.smilecard.SmileCardAdditionalCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.CashbackUnit;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.CashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.OrderUnitKey;
import com.ebaykorea.payback.core.repository.PayRewardRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper.CashbackOrderDetailEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper.CashbackOrderEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper.CashbackOrderMemberEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper.CashbackPolicyEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper.SmilecardCashbackOrderEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper.SmilecardT2T3CashbackEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.CashbackOrderDetailRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.CashbackOrderMemberRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.CashbackOrderPolicyRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.CashbackOrderRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.SmilecardCashbackOrderRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.SmilecardT2T3CashbackRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.CashbackOrderPolicyEntity;
import com.ebaykorea.payback.util.support.Conditioner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;

@Profile(GMARKET_TENANT)
@Service
@RequiredArgsConstructor
public class GmarketPayRewardRepository implements PayRewardRepository {

  private final CashbackOrderRepository cashbackOrderRepository;
  private final CashbackOrderPolicyRepository cashbackOrderPolicyRepository;
  private final CashbackOrderDetailRepository cashbackOrderDetailRepository;
  private final CashbackOrderMemberRepository cashbackOrderMemberRepository;
  private final SmilecardCashbackOrderRepository smilecardCashbackOrderRepository;
  private final SmilecardT2T3CashbackRepository smilecardT2T3CashbackRepository;

  private final CashbackOrderEntityMapper cashbackOrderEntityMapper;
  private final Conditioner<CashbackPolicyEntityMapper> cashbackPolicyEntityMapperConditioner;
  private final CashbackOrderDetailEntityMapper cashbackOrderDetailEntityMapper;
  private final CashbackOrderMemberEntityMapper cashbackOrderMemberEntityMapper;
  private final SmilecardCashbackOrderEntityMapper smilecardCashbackOrderEntityMapper;
  private final SmilecardT2T3CashbackEntityMapper smilecardT2T3CashbackEntityMapper;

  @Transactional
  @Override
  public void save(final PayCashback payCashback) {
    payCashback.getCashbacks()
        .forEach(cashback -> {
          //cashback_order
          saveCashbackUnits(payCashback, cashback, cashback.findAppliedCashbackUnits());

          //cashback_order_policy
          saveCashbackPolicies(payCashback, cashback, cashback.findCashbackPolicies());

          //cashback_order_detail
          saveCashbackDetail(payCashback, cashback);
        });

    //cashback_order_member
    saveCashbackMember(payCashback);

    if (payCashback.hasSmileCardCashbackAmount()) {
      //SMILECARD_CASHBACK_ORDER
      //2023.12.01: 스마일카드 타입 추가
      saveSmileCardCashback(payCashback, payCashback.getSmileCardCashback());

      if (payCashback.hasSmileCardAdditionalCashbacks()) {
        //smilecard_t2t3_cashback_info
        //2023.12.01: t3는 smilecard_t2t3_cashback_info 테이블에 넣지 않는다.
        saveSmileCardAdditionalCashback(payCashback, payCashback.getSmileCardCashback());
      }
    }
  }

  @Override
  public boolean hasAlreadySaved(KeyMap keyMap) {
    return keyMap.getOrderUnitKeys().stream()
        .map(OrderUnitKey::getBuyOrderNo)
        .map(cashbackOrderDetailRepository::findById)
        .anyMatch(Optional::isPresent);
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

  private void saveSmileCardCashback(final PayCashback payCashback, final SmileCardCashback smileCardCashback) {
    final var smileCardCashbackOrderEntity = smilecardCashbackOrderEntityMapper.map(payCashback, smileCardCashback);
    smilecardCashbackOrderRepository.save(smileCardCashbackOrderEntity);
  }

  private void saveSmileCardAdditionalCashback(final PayCashback payCashback, final SmileCardCashback smileCardCashback) {
    smileCardCashback.getAdditionalCashbacks().stream()
        .filter(SmileCardAdditionalCashback::isApply)
        .map(t2SmileCardCashback -> smilecardT2T3CashbackEntityMapper.map(payCashback, t2SmileCardCashback))
        .forEach(smilecardT2T3CashbackRepository::save);
  }
}
