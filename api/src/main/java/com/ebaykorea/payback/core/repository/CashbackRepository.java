package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.entity.cashback.PayCashback;

public interface CashbackRepository {
  void save(PayCashback payCashback);
}
