package com.ebaykorea.payback.core.domain.query.mapper;

import com.ebaykorea.payback.core.client.reward.dto.CashbackResponseDto;
import com.ebaykorea.payback.core.domain.entity.reward.CashbackOrder;
import com.ebaykorea.payback.core.domain.query.dto.CashbackOrderQuery;
import com.ebaykorea.payback.core.domain.query.dto.CashbackRewardQuery;
import com.ebaykorea.payback.core.persistence.mssql.stardb.reward.entity.CashbackOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CashbackQueryMapper {
    CashbackRewardQuery as (CashbackResponseDto response);
    CashbackOrderQuery as (CashbackOrder cashbackOrder , CashbackResponseDto response);
    CashbackOrder as(CashbackOrderEntity entity);

}
