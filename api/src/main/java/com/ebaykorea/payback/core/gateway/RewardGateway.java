package com.ebaykorea.payback.core.gateway;

import com.ebaykorea.payback.infrastructure.data.cashback.CashbackResponse;
import com.ebaykorea.payback.infrastructure.gateway.client.dto.CashbackRewardRequestDto;

public interface RewardGateway {
  CashbackResponse getCashbackReward(final CashbackRewardRequestDto request);
}
