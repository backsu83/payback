package com.ebaykorea.payback.infrastructure.query.mapper;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
import com.ebaykorea.payback.infrastructure.query.data.ReviewRewardQueryResult;
import com.ebaykorea.payback.util.PaybackInstants;
import java.sql.Timestamp;
import java.time.Instant;
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
  @Mapping(expression = "java(mapToStatus(entity.getStatus()))" , target = "save")
  ReviewRewardQueryResult map(SmileCashEventEntity entity, EventType eventType);

  @Mapping(expression = "java(mapToReasonCode(entity.getReasonCode()))", target = "reviewType")
  @Mapping(expression = "java(mapToSaveStatus(entity.getSaveStatus()))" , target = "save")
  @Mapping(expression = "java(mapToSaveDate(entity.getUpdateDate() , entity.getSaveStatus()))" , target = "saveDate")
  ReviewRewardQueryResult map(SmileCashSaveQueueEntity entity);


  default boolean mapToStatus(int status) { return status == 50;}

  default boolean mapToSaveStatus(int status) {return status == 1;}

  default EventType mapToReasonCode(String code) {return EventType.auctionCodeOf(code);}

  default Instant mapToSaveDate(Timestamp date, int status) {
    if(status == 1) {
      return PaybackInstants.from(date);
    }
    return null;
  }

}