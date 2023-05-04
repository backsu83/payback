package com.ebaykorea.payback.infrastructure.persistence.mapper;

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.infrastructure.query.data.SsgPointTargetQueryResult;
import com.ebaykorea.payback.util.PaybackInstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {PaybackInstants.class}
)
public interface SsgPointTargetQueryResultMapper {

    @Mapping(source = "saveAmount", target = "ssgPointSaveAmount")
    @Mapping(expression = "java(PaybackInstants.DATE_FORMATTER.format(source.getScheduleDate()))", target = "ssgPointSaveExpectDate")
    @Mapping(source = "pointStatus", target = "ssgPointSavedYN")
    SsgPointTargetQueryResult map(SsgPointTargetEntity source);

    default String mapToSavedYN(final String pointStatus) {
        return pointStatus.equals("SS") ? "Y" : "N";
    }
}
