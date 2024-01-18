package com.ebaykorea.payback.infrastructure.query.mapper;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
import com.ebaykorea.payback.infrastructure.query.data.ReviewRewardQueryResult;
import com.ebaykorea.payback.util.PaybackBooleans;
import com.ebaykorea.payback.util.PaybackInstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {PaybackInstants.class}
)
public interface ReviewRewardQueryMapper {

  @Mapping(source = "eventType", target = "reviewType")
  @Mapping(source = "entity.requestMoney", target = "saveAmount")
  @Mapping(expression = "java(mapToStatusYN(entity.getStatus()))" , target = "save")
  ReviewRewardQueryResult map(SmileCashEventEntity entity, EventType eventType);

  @Mapping(expression = "java(mapToReasonCode(entity.getReasonCode()))", target = "reviewType")
  @Mapping(source = "saveAmount", target = "saveAmount")
  @Mapping(expression = "java(mapToSaveStatusYN(entity.getSaveStatus()))" , target = "save")
  @Mapping(expression = "java(PaybackInstants.from(entity.getUpdateDate()))" , target = "saveDate")
  ReviewRewardQueryResult map(SmileCashSaveQueueEntity entity);


  default String mapToStatusYN(int status) {
    return PaybackBooleans.toYN(status == 50);
  }

  default String mapToSaveStatusYN(int status) {return PaybackBooleans.toYN(status == 1);}

  default EventType mapToReasonCode(String code) {return EventType.auctionCodeOf(code);}
}