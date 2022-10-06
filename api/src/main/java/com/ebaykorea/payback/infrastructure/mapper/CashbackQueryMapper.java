package com.ebaykorea.payback.infrastructure.mapper;

import com.ebaykorea.payback.infrastructure.data.cashback.CashbackResponse;
import com.ebaykorea.payback.infrastructure.data.cashback.CashbackOrderQuery;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CashbackQueryMapper {
  CashbackOrderQuery as(CashbackOrderEntity cashbackOrder, CashbackResponse response);

}
