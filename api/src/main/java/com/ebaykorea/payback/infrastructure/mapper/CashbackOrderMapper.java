package com.ebaykorea.payback.infrastructure.mapper;

import com.ebaykorea.payback.api.dto.CashbackOrderRequestDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderEntityId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CashbackOrderMapper {
  CashbackOrderEntityId of(final CashbackOrderRequestDto request);
}
