package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper;

import com.ebaykorea.payback.core.domain.entity.event.request.SmileCashEvent;
import com.ebaykorea.payback.core.domain.entity.event.request.SmileCashEventResult;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventRequestEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventResultEntity;
import java.sql.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = Timestamp.class
)
public interface SmileCashEventEntityMapper {

  @Mapping(source = "saveIntegrationType.code", target = "saveIntegrationType")
  @Mapping(source = "saveAmount", target = "requestMoney")
  @Mapping(source = "saveAmount", target = "requestOutputDisabledMoney")
  @Mapping(source = "eventType.gmarketCode", target = "cashBalanceType")
  @Mapping(source = "memberKey", target = "custNo")
  @Mapping(expression = "java(Timestamp.from(smileCashEvent.getExpirationDate()))", target = "expireDate")
  @Mapping(source = "requestNo", target = "refNo")
  @Mapping(source = "memberKey", target = "regId")
  @Mapping(source = "eventNo", target = "eid")
  SmileCashEventRequestEntity map(SmileCashEvent smileCashEvent);

  @Mapping(source = "source.result", target = "resultCode")
  @Mapping(source = "source.smilePayNo", target = "savingNo")
  EventRewardResultDto map(final Long requestNo, final SmileCashEventResultEntity source);

  @Mapping(expression = "java(source.getStatus() == 50)", target = "saved")
  @Mapping(expression = "java(source.getStatus() >= 90)", target = "failed")
  SmileCashEventResult map(SmileCashEventEntity source);
}
