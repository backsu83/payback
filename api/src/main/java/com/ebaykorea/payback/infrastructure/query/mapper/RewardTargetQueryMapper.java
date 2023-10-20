package com.ebaykorea.payback.infrastructure.query.mapper;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmilecardCashbackOrderEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.ssgpoint.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.infrastructure.query.data.CashbackTargetQueryData;
import com.ebaykorea.payback.infrastructure.query.data.SmileCardQueryData;
import com.ebaykorea.payback.infrastructure.query.data.SsgPointTargetUnitQueryData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.time.Instant;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {CashbackType.class}
)
public interface RewardTargetQueryMapper {

  @Mapping(expression = "java(CashbackType.toCashbackName(cashbackType))", target = "cashbackType")
  CashbackTargetQueryData map(String cashbackType, BigDecimal saveAmount, Instant expectSaveDate);

  @Mapping(constant = "", target = "type") //TODO: DB 컬럼 추가 시 작업
  @Mapping(source = "cashbackAmount", target = "saveAmount")
  @Mapping(constant = "10", target = "expectSaveDays")
  @Mapping(source = "t2t3CashbackAmount", target = "additionalSaveAmount")
  @Mapping(expression = "java(source.getT2ExpectSaveDate(30))", target = "additionalExpectSaveDate")
  SmileCardQueryData map(SmilecardCashbackOrderEntity source);

  @Mapping(source = "scheduleDate", target = "expectSaveDate")
  SsgPointTargetUnitQueryData map(final SsgPointTargetEntity sources);
}
