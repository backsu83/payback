package com.ebaykorea.payback.port.mapper;

import com.ebaykorea.payback.adapter.rest.dto.CashbackRequest;
import com.ebaykorea.payback.port.service.reward.client.dto.CashbackRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QueryRequestMapper {
    CashbackRequestDto as(CashbackRequest query);
}
