package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.core.domain.entity.cashback.Cashbacks;
import com.ebaykorea.payback.core.repository.CashbackRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderMemberRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderDetailRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CashbackRepositoryImpl implements CashbackRepository {

  final private CashbackOrderRepository cashbackOrderRepository;
  final private CashbackOrderDetailRepository cashbackOrderDetailRepository;
  final private CashbackOrderMemberRepository cashbackOrderMemberRepository;
  final private CashbackOrderPolicyRepository cashbackOrderPolicyRepository;

  @Transactional
  @Override
  public void save(Cashbacks cashbacks) {
    //TODO

  }
}
