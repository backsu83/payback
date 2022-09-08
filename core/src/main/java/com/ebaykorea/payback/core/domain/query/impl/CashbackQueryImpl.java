package com.ebaykorea.payback.core.domain.query.impl;

import com.ebaykorea.payback.core.client.reward.dto.CashbackRequestDto;
import com.ebaykorea.payback.core.client.reward.dto.CashbackResponseDto;
import com.ebaykorea.payback.core.domain.entity.reward.CashbackOrder;
import com.ebaykorea.payback.core.domain.query.CashbackQuery;
import com.ebaykorea.payback.core.domain.query.dto.CashbackOrderQuery;
import com.ebaykorea.payback.core.domain.query.dto.CashbackRewardQuery;
import com.ebaykorea.payback.core.domain.query.mapper.CashbackQueryMapper;
import com.ebaykorea.payback.core.domain.service.reward.CashbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class CashbackQueryImpl implements CashbackQuery {

    private final CashbackService cashbackService;
    private final CashbackQueryMapper mapper;

    @Override
    public CashbackOrderQuery getCashbackOrder(long buyOrderNo , CashbackRequestDto request) {
        CashbackResponseDto apiresponse = cashbackService.getCashbackReward(request);
        CashbackOrder dbquery = cashbackService.getCashbackOrderId(buyOrderNo);
        CashbackOrderQuery result = mapper.as(dbquery, apiresponse);
        return result;
    }

    @Override
    public CashbackRewardQuery getCashbackReward(final CashbackRequestDto request) {
        return mapper.as(cashbackService.getCashbackReward(request));
    }


}
