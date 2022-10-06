package com.ebaykorea.payback.core.gateway;

import com.ebaykorea.payback.infrastructure.data.cashback.CashbackResponse;
import com.ebaykorea.payback.infrastructure.gateway.client.dto.CashbackRequestDataDto;

public interface RewardGateway {
  CashbackResponse getCashbackReward(final CashbackRequestDataDto request);
}
