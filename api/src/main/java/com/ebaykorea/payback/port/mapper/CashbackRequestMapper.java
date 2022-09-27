package com.ebaykorea.payback.port.mapper;

import com.ebaykorea.payback.adapter.rest.dto.CashbackRequestDto;
import com.ebaykorea.payback.port.gateway.client.dto.CashbackRequestDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CashbackRequestMapper {
    CashbackRequestDataDto of(CashbackRequestDto query);
}
