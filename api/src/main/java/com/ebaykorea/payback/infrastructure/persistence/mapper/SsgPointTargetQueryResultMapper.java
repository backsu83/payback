package com.ebaykorea.payback.infrastructure.persistence.mapper;

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.infrastructure.query.data.SsgPointTargetQueryResult;
import com.ebaykorea.payback.util.PaybackDateTimeFormatters;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {PaybackDateTimeFormatters.class}
)
public interface SsgPointTargetQueryResultMapper {

    @Mapping(source = "saveAmount", target = "ssgPointSaveAmount")
    @Mapping(expression = "java(PaybackDateTimeFormatters.DATE_FORMATTER.format(source.getScheduleDate()))", target = "ssgPointSaveExpectDate")
    SsgPointTargetQueryResult map(SsgPointTargetEntity source);
}
