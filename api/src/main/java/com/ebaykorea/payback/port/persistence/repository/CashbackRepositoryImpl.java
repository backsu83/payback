package com.ebaykorea.payback.port.persistence.repository;

import com.ebaykorea.payback.core.domain.model.Cashbacks;
import com.ebaykorea.payback.core.repository.CashbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashbackRepositoryImpl implements CashbackRepository {

  @Override
  public void save(Cashbacks cashbacks) {
    //TODO
  }
}
