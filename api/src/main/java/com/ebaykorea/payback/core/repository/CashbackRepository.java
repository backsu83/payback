package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.model.Cashbacks;

public interface CashbackRepository {
  void save(final Cashbacks cashbacks);
}
