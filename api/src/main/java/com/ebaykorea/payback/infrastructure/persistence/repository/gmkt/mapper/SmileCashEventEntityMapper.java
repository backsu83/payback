package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper;

import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardResultDto;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventRequestEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventResultEntity;
import com.ebaykorea.payback.util.PaybackInstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;

import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate;

@Mapper(
    componentModel = "spring"
)
public interface SmileCashEventEntityMapper {

  @Mapping(source = "request.saveAmount", target = "requestMoney")
  @Mapping(source = "request.saveAmount", target = "requestOutputDisabledMoney")
  @Mapping(constant = "G9", target = "cashBalanceType")
  @Mapping(source = "memberKey", target = "custNo")
  @Mapping(expression = "java(getExpireDate())", target = "expireDate")
  @Mapping(source = "request.requestNo", target = "refNo")
  @Mapping(constant = "8166", target = "ersNo")
  @Mapping(source = "memberKey", target = "regId")
  SmileCashEventRequestEntity map(String memberKey, MemberEventRewardRequestDto request);


  @Mapping(source = "request.status", target = "approvalStatus")
  @Mapping(source = "request.tryCount", target = "tryCount")
  @Mapping(source = "request.operator", target = "regId")
  SmileCashEventRequestEntity map(Long smilePayNo, SetEventRewardRequestDto request);

  default Timestamp getExpireDate() {
    return Timestamp.from(getDefaultEnableDate(PaybackInstants.now()));
  }

  @Mapping(source = "source.result", target = "resultCode")
  MemberEventRewardResultDto map(final Long requestNo, final SmileCashEventResultEntity source);

  @Mapping(expression = "java(source.getStatus() == 50)", target = "saved")
  @Mapping(expression = "java(source.getStatus() == 90)", target = "failed")
  SmileCashEvent map(SmileCashEventEntity source);
}
