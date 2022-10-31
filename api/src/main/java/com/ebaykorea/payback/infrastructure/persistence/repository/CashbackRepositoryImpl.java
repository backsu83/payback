package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.core.domain.entity.cashback.Cashbacks;
import com.ebaykorea.payback.core.repository.CashbackRepository;
import com.ebaykorea.payback.infrastructure.persistence.mapper.CashbackEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderDetailRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderMemberRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderPolicyRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CashbackRepositoryImpl implements CashbackRepository {

  private final CashbackOrderRepository cashbackOrderRepository;
  private final CashbackOrderDetailRepository cashbackOrderDetailRepository;
  private final CashbackOrderPolicyRepository cashbackOrderPolicyRepository;
  private final CashbackOrderMemberRepository cashbackOrderMemberRepository;
  private final CashbackEntityMapper cashbackEntityMapper;

  @Transactional
  @Override
  public void save(final Cashbacks cashbacks) {

    final var list = cashbacks.getCashbacks();

    list.forEach(cashback -> {
      cashbackOrderRepository.save(cashbackEntityMapper.map(cashbacks, cashback));
      cashbackOrderDetailRepository.save(cashbackEntityMapper.mapToDetail(cashbacks, cashback));
      cashbacks.getPolicies().stream()
          .filter(o -> o.getOrderNo() == cashback.getOrderNo())
          .forEach(policy -> {
            cashbackOrderPolicyRepository.save(cashbackEntityMapper.mapToPolicy(cashbacks, policy));
          });
    });
    if(cashbacks.getMember().isSmileClubMember())
      cashbackOrderMemberRepository.save(cashbackEntityMapper.mapToMember(cashbacks));
  }

}
