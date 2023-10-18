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

  @Mapping(expression = "java(CashbackType.fromDBCode(cashbackType))", target = "cashbackType")
  CashbackTargetQueryData map(String cashbackType, BigDecimal totalAmount, Instant expectSaveDate);

  @Mapping(expression = "java(source.getSmileCardCashbackAmount())", target = "smileCardCashbackAmount")
  @Mapping(expression = "java(source.getT2SmileCardCashbackAmount())", target = "t2SmileCardCashbackAmount")
  SmileCardQueryData map(SmilecardCashbackOrderEntity source);

  @Mapping(source = "saveAmount", target = "amount")
  @Mapping(source = "scheduleDate", target = "expectSaveDate")
  SsgPointTargetUnitQueryData map(final SsgPointTargetEntity sources);
}
