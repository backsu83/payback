package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper;

import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate;

import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.CashEventRewardReqest;
import com.ebaykorea.payback.core.dto.event.CashEventRewardResult;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventRequestEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventResultEntity;
import com.ebaykorea.payback.util.PaybackInstants;
import java.sql.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SmileCashEventEntityMapper {

  @Mapping(source = "reqest.saveAmount", target = "requestMoney")
  @Mapping(source = "reqest.saveAmount", target = "requestOutputDisabledMoney")
  @Mapping(source = "reqest.balanceCode", target = "cashBalanceType")
  @Mapping(expression = "java(reqest.getEventType().getGmarketCode())", target = "smilecashCode") //임시
  @Mapping(source = "reqest.requestId", target = "custNo")
  @Mapping(expression = "java(getExpireDate())", target = "expireDate")
  @Mapping(source = "reqest.requestNo", target = "refNo")
  @Mapping(constant = "8166", target = "ersNo")
  @Mapping(source = "reqest.requestId", target = "regId")
  SmileCashEventRequestEntity map(CashEventRewardReqest reqest);

  @Mapping(source = "request.status", target = "approvalStatus")
  @Mapping(source = "request.tryCount", target = "tryCount")
  @Mapping(source = "request.operator", target = "regId")
  SmileCashEventRequestEntity map(Long smilePayNo, SetEventRewardRequestDto request);

  default Timestamp getExpireDate() {
    return Timestamp.from(getDefaultEnableDate(PaybackInstants.now()));
  }

  @Mapping(source = "source.result", target = "resultCode")
  CashEventRewardResult map(final Long requestNo, final SmileCashEventResultEntity source);

  @Mapping(expression = "java(source.getStatus() == 50)", target = "saved")
  @Mapping(expression = "java(source.getStatus() >= 90)", target = "failed")
  SmileCashEvent map(SmileCashEventEntity source);
}
