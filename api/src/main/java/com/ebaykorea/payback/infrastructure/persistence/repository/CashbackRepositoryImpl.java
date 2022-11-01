package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.core.domain.entity.cashback.PayCashback;
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
  public void save(PayCashback payCashback) {

    //TODO: PayCashback이 완성되면 좀더 간단하게 구현될 수 있습니다

//    final var list = payCashback.getCashbacks();
//
//    list.forEach(cashback -> {
//      cashbackOrderRepository.save(cashbackEntityMapper.map(payCashback, cashback));
//      cashbackOrderDetailRepository.save(cashbackEntityMapper.mapToDetail(payCashback, cashback));
//      payCashback.getCashbackPolicies().stream()
//          .filter(o -> o.getOrderNo() == cashback.getOrderNo())
//          .forEach(policy -> {
//            cashbackOrderPolicyRepository.save(cashbackEntityMapper.mapToPolicy(payCashback, policy));
//          });
//    });
//    if(payCashback.getMember().isSmileClubMember())
//      cashbackOrderMemberRepository.save(cashbackEntityMapper.mapToMember(payCashback));
  }

}
