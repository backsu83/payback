package com.ebaykorea.payback.scheduler.domain.mapper;


import com.ebaykorea.payback.scheduler.domain.entity.PaybackBatchRecord;
import com.ebaykorea.payback.scheduler.infrastructure.repository.entity.CashbackOrderBatchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PaybackBatchRecordMapper {

  PaybackBatchRecord map(CashbackOrderBatchEntity entity);

}
