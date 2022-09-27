package com.ebaykorea.payback.port.mapper;

import com.ebaykorea.payback.port.data.cashback.CashbackResponse;
import com.ebaykorea.payback.port.data.cashback.CashbackOrderQuery;
import com.ebaykorea.payback.port.persistence.repository.stardb.entity.CashbackOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CashbackQueryMapper {
  CashbackOrderQuery as(CashbackOrderEntity cashbackOrder, CashbackResponse response);

}
