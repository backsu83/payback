package com.ebaykorea.payback.port.mapper;

import com.ebaykorea.payback.adapter.rest.dto.CashbackOrderRequestDto;
import com.ebaykorea.payback.port.persistence.repository.stardb.entity.CashbackOrderEntityId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CashbackOrderMapper {
  CashbackOrderEntityId of(final CashbackOrderRequestDto request);
}
