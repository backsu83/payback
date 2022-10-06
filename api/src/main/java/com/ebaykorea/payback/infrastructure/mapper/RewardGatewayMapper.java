package com.ebaykorea.payback.infrastructure.mapper;

import com.ebaykorea.payback.infrastructure.data.cashback.CashbackResponse;
import com.ebaykorea.payback.infrastructure.gateway.client.dto.CashbackResponseDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RewardGatewayMapper {
    CashbackResponse of(CashbackResponseDataDto cashbackRewardQuery);
}
