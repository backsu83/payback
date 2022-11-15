package com.ebaykorea.payback.scheduler.service.mapper;

import com.ebaykorea.payback.scheduler.infrastructure.entity.CashbackCheckerEntity;
import com.ebaykorea.payback.scheduler.service.dto.CashbackCheckerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CashbackCheckerMapper {

  CashbackCheckerDto map(CashbackCheckerEntity entity);
}
