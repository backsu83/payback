package com.ebaykorea.payback.batch.job.mapper;

import com.ebaykorea.payback.batch.domain.SsgPointProcessorDto;
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {
        OrderSiteType.class,
        PointStatusType.class,
        PointTradeType.class,
    }
)
public interface SsgPointProcessorMapper {

    @Mapping(expression = "java(OrderSiteType.forValue(entity.getSiteType()))", target = "siteType")
    @Mapping(expression = "java(PointTradeType.from(entity.getTradeType()))", target = "tradeType")
    @Mapping(expression = "java(PointStatusType.from(entity.getPointStatus()))", target = "status")
    SsgPointProcessorDto map(SsgPointTargetEntity entity);

}
