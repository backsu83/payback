package com.ebaykorea.payback.infrastructure.mapper;

import com.ebaykorea.payback.api.dto.CashbackRequestDto;
import com.ebaykorea.payback.infrastructure.gateway.client.dto.CashbackRequestDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CashbackRequestMapper {
    CashbackRequestDataDto of(CashbackRequestDto query);
}
