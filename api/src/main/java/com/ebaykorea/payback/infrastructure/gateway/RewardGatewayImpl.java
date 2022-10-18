package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.infrastructure.mapper.RewardGatewayMapper;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.RewardApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RewardGatewayImpl implements RewardGateway {
  private final RewardApiClient rewardApiClient;
  private final RewardGatewayMapper responseMapper;
}
