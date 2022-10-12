package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.core.domain.model.Cashbacks;
import com.ebaykorea.payback.core.repository.CashbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CashbackRepositoryImpl implements CashbackRepository {

  @Transactional
  @Override
  public void save(Cashbacks cashbacks) {
    //TODO
  }
}
