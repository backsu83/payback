package com.ebaykorea.payback.infrastructure.query.mapper;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.SmilecardCashbackOrderEntity;
import com.ebaykorea.payback.infrastructure.query.data.CashbackOrderQueryData;
import com.ebaykorea.payback.infrastructure.query.data.SmileCardQueryData;
import com.ebaykorea.payback.infrastructure.query.data.SsgPointTargetUnitQueryData;
import com.ebaykorea.payback.util.PaybackInstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {PaybackInstants.class, CashbackType.class}
)
public interface RewardTargetQueryMapper {

  @Mapping(expression = "java(CashbackType.fromDBCode(source.getCashbackType()))", target = "cashbackType")
  @Mapping(expression = "java(PaybackInstants.from(source.getUseEnableDt()))", target = "expectSaveDate")
  CashbackOrderQueryData map(CashbackOrderEntity source);

  @Mapping(expression = "java(source.getSmileCardCashbackAmount())", target = "smileCardCashbackAmount")
  @Mapping(expression = "java(source.getT2SmileCardCashbackAmount())", target = "t2SmileCardCashbackAmount")
  SmileCardQueryData map(SmilecardCashbackOrderEntity source);

  @Mapping(source = "saveAmount", target = "amount")
  @Mapping(source = "scheduleDate", target = "expectSaveDate")
  SsgPointTargetUnitQueryData map(final SsgPointTargetEntity sources);
}
