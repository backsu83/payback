package com.ebaykorea.payback.port.mapper;

import com.ebaykorea.payback.adapter.rest.dto.CashbackOrderResponse;
import com.ebaykorea.payback.adapter.rest.dto.CashbackResponse;
import com.ebaykorea.payback.core.domain.query.dto.CashbackOrderQuery;
import com.ebaykorea.payback.core.domain.query.dto.CashbackRewardQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QueryResponseMapper {
    CashbackResponse as(CashbackRewardQuery cashbackRewardQuery);
    CashbackOrderResponse as(CashbackOrderQuery cashbackQuery);
}
