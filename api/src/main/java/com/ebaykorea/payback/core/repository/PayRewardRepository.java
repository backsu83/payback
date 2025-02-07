package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.entity.cashback.PayCashback;
import com.ebaykorea.payback.core.domain.entity.order.KeyMap;

public interface PayRewardRepository {
  void save(PayCashback payCashback);

  boolean hasAlreadySaved(KeyMap keyMap);
}
