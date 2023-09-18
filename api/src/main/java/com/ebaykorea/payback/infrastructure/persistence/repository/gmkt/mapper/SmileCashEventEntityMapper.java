package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper;

import com.ebaykorea.payback.core.dto.member.MemberCashbackRequestDto;
import com.ebaykorea.payback.core.dto.member.MemberCashbackResultDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
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
  @Mapping(source = "request.eventType.cashBalanceCode", target = "cashBalanceType")
  @Mapping(source = "memberKey", target = "custNo")
  @Mapping(expression = "java(getExpireDate())", target = "expireDate")
  @Mapping(source = "request.requestNo", target = "refNo")
  @Mapping(source = "request.eventType.eventNo", target = "ersNo")
  @Mapping(source = "memberKey", target = "regId")
  SmileCashEventEntity map(String memberKey, MemberCashbackRequestDto request);

  //TODO: 만료 정책 확인
  default Timestamp getExpireDate() {
    return Timestamp.from(getDefaultEnableDate(PaybackInstants.now()));
  }

  @Mapping(source = "source.result", target = "resultCode")
  MemberCashbackResultDto map(final Long requestNo, final SmileCashEventResultEntity source);



}
