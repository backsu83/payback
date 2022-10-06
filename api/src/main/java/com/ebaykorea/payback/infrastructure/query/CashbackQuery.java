package com.ebaykorea.payback.infrastructure.query;

import com.ebaykorea.payback.infrastructure.mapper.CashbackQueryMapper;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.core.repository.CashbackOrderRepository;
import com.ebaykorea.payback.infrastructure.data.cashback.CashbackOrderQuery;
import com.ebaykorea.payback.infrastructure.gateway.client.dto.CashbackRequestDataDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderEntityId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

//TODO: 임시 코드
@Component
@RequiredArgsConstructor
public class CashbackQuery {
  private final RewardGateway rewardGateway;
  private final CashbackOrderRepository cashbackOrderRepository;
  private final CashbackQueryMapper cashbackQueryMapper;

  public CashbackOrderQuery getCashbackOrder(final CashbackOrderEntityId id, final CashbackRequestDataDto request) {
    final var cashbackOrder = cashbackOrderRepository.findById(id).orElse(null);
    final var cashbackReward = rewardGateway.getCashbackReward(request);

    return cashbackQueryMapper.as(cashbackOrder, cashbackReward);
  }
}
