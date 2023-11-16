package com.ebaykorea.payback.infrastructure.query.mapper;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.ssgpoint.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.infrastructure.query.data.SsgPointTargetQueryResult;
import com.ebaykorea.payback.util.PaybackDateTimeFormatters;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {PaybackDateTimeFormatters.class}
)
public interface SsgPointTargetQueryResultMapper {

    @Mapping(source = "saveAmount", target = "ssgPointSaveAmount")
    @Mapping(expression = "java(PaybackDateTimeFormatters.DATE_FORMATTER.format(source.getScheduleDate()))", target = "ssgPointSaveExpectDate")
    @Mapping(source = "pointStatus", target = "ssgPointSavedYN", qualifiedByName = "mapToSavedYN")
    @Mapping(source = "tradeType", target = "ssgPointTradeType")
    SsgPointTargetQueryResult map(SsgPointTargetEntity source);

    @Named("mapToSavedYN")
    default String mapToSavedYN(final String pointStatus) {
        return pointStatus.equals(PointStatusType.Success.getCode()) ? "Y" : "N";
    }
}
