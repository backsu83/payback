package com.ebaykorea.payback.port.mapper;

import com.ebaykorea.payback.port.data.cashback.CashbackResponse;
import com.ebaykorea.payback.port.gateway.client.dto.CashbackResponseDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RewardGatewayMapper {
    CashbackResponse of(CashbackResponseDataDto cashbackRewardQuery);
}
