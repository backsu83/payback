package com.ebaykorea.payback.infrastructure.query.mapper;


import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
import com.ebaykorea.payback.infrastructure.query.data.ReviewRewardQueryResult;
import com.ebaykorea.payback.util.PaybackBooleans;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewRewardQueryMapper {

  @Mapping(expression = "java(eventType.getName())", target = "reviewType")
  @Mapping(source = "entity.requestMoney", target = "amount")
  @Mapping(expression = "java(mapToStatusYN(entity.getStatus()))" , target = "isSave")
  ReviewRewardQueryResult map(SmileCashEventEntity entity, EventType eventType);

  default String mapToStatusYN(int status) {
    return PaybackBooleans.toYN(status == 50);
  }
}
