package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventRequestEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventResultEntity;
import com.ebaykorea.payback.util.PaybackInstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SmileCashEventEntityMapper {

  @Mapping(source = "saveAmount", target = "requestMoney")
  @Mapping(source = "saveAmount", target = "requestOutputDisabledMoney")
  @Mapping(source = "eventType.gmarketCode", target = "cashBalanceType")
  @Mapping(source = "memberKey", target = "custNo")
  @Mapping(expression = "java(getExpireDate(request.getExpirationDate()))", target = "expireDate")
  @Mapping(source = "requestNo", target = "refNo")
  @Mapping(expression = "java(getErsNo(request.getEventType()))", target = "ersNo")
  @Mapping(source = "memberKey", target = "regId")
  SmileCashEventRequestEntity map(EventRewardRequestDto request);

  @Mapping(source = "request.status", target = "approvalStatus")
  @Mapping(source = "request.tryCount", target = "tryCount")
  @Mapping(source = "request.operator", target = "regId")
  SmileCashEventRequestEntity map(Long smilePayNo, SetEventRewardRequestDto request);

  default Timestamp getExpireDate(final Instant expirationDate) {
    return Optional.ofNullable(expirationDate)
        .map(Timestamp::from)
        .orElse(Timestamp.from(getDefaultEnableDate(PaybackInstants.now())));
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
