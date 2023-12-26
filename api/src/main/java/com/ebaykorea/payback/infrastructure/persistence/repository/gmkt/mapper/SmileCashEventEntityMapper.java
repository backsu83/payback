package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper;

import static com.ebaykorea.payback.util.PaybackInstants.truncatedDays;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventRequestEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventResultEntity;
import com.ebaykorea.payback.util.PaybackInstants;
import java.sql.Timestamp;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SmileCashEventEntityMapper {

  @Mapping(source = "saveAmount", target = "requestMoney")
  @Mapping(source = "saveAmount", target = "requestOutputDisabledMoney")
  @Mapping(source = "eventType.gmarketCode", target = "cashBalanceType")
  @Mapping(source = "memberKey", target = "custNo")
  @Mapping(expression = "java(getExpireDate(request))", target = "expireDate")
  @Mapping(source = "requestNo", target = "refNo")
  @Mapping(expression = "java(getErsNo(request.getEventType()))", target = "ersNo")
  @Mapping(source = "memberKey", target = "regId")
  @Mapping(source = "eventNo", target = "eid")
  SmileCashEventRequestEntity map(EventRewardRequestDto request);

  @Mapping(source = "request.status", target = "approvalStatus")
  @Mapping(source = "request.tryCount", target = "tryCount")
  @Mapping(source = "request.operator", target = "regId")
  SmileCashEventRequestEntity map(Long smilePayNo, SetEventRewardRequestDto request);

  default Timestamp getExpireDate(final EventRewardRequestDto request) {
    return Optional.ofNullable(request.getExpirationDate())
        .map(Timestamp::from)
        .orElse(Timestamp.from(truncatedDays(PaybackInstants.now(), request.getEventType().getPeriod())));
  }

  default int getErsNo(final EventType eventType) {
    return eventType == EventType.Toss ? 8166 : 0;
  }

  @Mapping(source = "source.result", target = "resultCode")
  EventRewardResultDto map(final Long requestNo, final SmileCashEventResultEntity source);

  @Mapping(expression = "java(source.getStatus() == 50)", target = "saved")
  @Mapping(expression = "java(source.getStatus() >= 90)", target = "failed")
  SmileCashEvent map(SmileCashEventEntity source);
}
