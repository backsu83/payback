package com.ebaykorea.payback.api.controller.mapper;

import com.ebaykorea.payback.api.controller.dto.CashbackRequest;
import com.ebaykorea.payback.core.client.reward.dto.CashbackRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QueryRequestMapper {
    CashbackRequestDto as(CashbackRequest query);
}
