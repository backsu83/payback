package com.ebaykorea.payback.core.gateway;

import com.ebaykorea.payback.port.data.cashback.CashbackResponse;
import com.ebaykorea.payback.port.gateway.client.dto.CashbackRequestDataDto;

public interface RewardGateway {
  CashbackResponse getCashbackReward(final CashbackRequestDataDto request);
}
