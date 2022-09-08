package com.ebaykorea.payback.core.domain.service.reward.impl;

import com.ebaykorea.payback.core.client.reward.RewardApiClient;
import com.ebaykorea.payback.core.client.reward.dto.CashbackRequestDto;
import com.ebaykorea.payback.core.client.reward.dto.CashbackResponseDto;
import com.ebaykorea.payback.core.client.reward.dto.RewardBaseResponse;
import com.ebaykorea.payback.core.domain.entity.reward.CashbackOrder;
import com.ebaykorea.payback.core.domain.query.mapper.CashbackQueryMapper;
import com.ebaykorea.payback.core.domain.service.reward.CashbackService;
import com.ebaykorea.payback.core.persistence.mssql.stardb.reward.CashbackOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CashbackServiceImpl implements CashbackService {

    private final CashbackOrderRepository cashbackOrderRepository;
    private final CashbackQueryMapper mapper;
    private final RewardApiClient rewardApiClient;

    @Override
    public CashbackResponseDto getCashbackReward(final CashbackRequestDto request) {
        RewardBaseResponse<CashbackResponseDto> response = rewardApiClient.cashbackReward(request);
        return response.getResult();
    }

    @Override
    @Transactional
    public CashbackOrder getCashbackOrderId(long buyOrderNo) {
        CashbackOrder cashbackOrder = mapper.as(cashbackOrderRepository.findByBuyOrderNo(buyOrderNo));
        return cashbackOrder;
    }

}
