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
import java.time.temporal.ChronoUnit;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {CashbackType.class, ChronoUnit.class}
)
public interface RewardTargetQueryMapper {

  @Mapping(expression = "java(CashbackType.toCashbackName(cashbackType))", target = "cashbackType")
  CashbackTargetQueryData map(String cashbackType, BigDecimal saveAmount, Instant expectSaveDate);

  @Mapping(source = "smileCardType", target = "type")
  @Mapping(expression = "java(source.getSmileCardCashbackAmount())", target = "saveAmount")
  @Mapping(constant = "10", target = "expectSaveDays")
  @Mapping(expression = "java(source.getSmileCardAdditionalSaveAmount())", target = "additionalSaveAmount")
  @Mapping(expression = "java(source.getT2ExpectSaveDate(30))", target = "additionalExpectSaveDate")
  SmileCardQueryData map(SmilecardCashbackOrderEntity source);

  @Mapping(expression = "java(source.getScheduleDate().truncatedTo(ChronoUnit.DAYS))", target = "expectSaveDate")
  SsgPointTargetUnitQueryData map(final SsgPointTargetEntity source);
}
