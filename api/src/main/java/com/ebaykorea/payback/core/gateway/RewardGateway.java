package com.ebaykorea.payback.core.gateway;

import com.ebaykorea.payback.infrastructure.data.cashback.CashbackResponse;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.CashbackRewardRequestDto;

public interface RewardGateway {
  CashbackResponse getCashbackReward(final CashbackRewardRequestDto request);
}
