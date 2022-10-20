package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.entity.cashback.Cashbacks;

public interface CashbackRepository {
  void save(Cashbacks cashbacks);
}
