package com.ebaykorea.payback.port.query;

import com.ebaykorea.payback.port.mapper.CashbackQueryMapper;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.core.repository.CashbackOrderRepository;
import com.ebaykorea.payback.port.data.cashback.CashbackOrderQuery;
import com.ebaykorea.payback.port.gateway.client.dto.CashbackRequestDataDto;
import com.ebaykorea.payback.port.persistence.repository.stardb.entity.CashbackOrderEntityId;
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
