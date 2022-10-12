package com.ebaykorea.payback.infrastructure.gateway.impl;

import com.ebaykorea.payback.infrastructure.data.cashback.CashbackResponse;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.dto.CashbackRequestDto;
import com.ebaykorea.payback.infrastructure.mapper.RewardGatewayMapper;
import com.ebaykorea.payback.infrastructure.gateway.client.RewardApiClient;
import com.ebaykorea.payback.infrastructure.gateway.client.dto.RewardBaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RewardGatewayImpl implements RewardGateway {
  private final RewardApiClient rewardApiClient;
  private final RewardGatewayMapper responseMapper;

  public CashbackResponse getCashbackReward(final CashbackRequestDto request) {
    return Optional.ofNullable(rewardApiClient.cashbackReward(request))
        .filter(RewardBaseResponse::isSuccess)
        .map(RewardBaseResponse::getResult)
        .map(responseMapper::of)
        .orElse(null); //TODO: 동작에 따른 올바른 리턴 결과 exception or null
  }
}
