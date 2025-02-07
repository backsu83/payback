package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper;

import com.ebaykorea.payback.core.domain.entity.cashback.PayCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.smilecard.SmileCardCashback;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmilecardCashbackOrderEntity;
import com.ebaykorea.payback.util.PaybackBooleans;
import com.ebaykorea.payback.util.PaybackTimestamps;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {PaybackTimestamps.class , PaybackBooleans.class}
)
public interface SmilecardCashbackOrderEntityMapper {

  @Mapping(source = "smileCardCashback.cashbackAmount", target = "cashbackAmount")
  @Mapping(expression = "java(smileCardCashback.sumAdditionalAmount())", target = "t2t3CashbackAmount")
  @Mapping(expression = "java(PaybackBooleans.toYN(smileCardCashback.isApply()))", target = "applyYn")
  @Mapping(expression = "java(PaybackBooleans.toYN(smileCardCashback.hasAdditionalCashback()))", target = "t2t3ApplyYn")
  @Mapping(expression = "java(PaybackTimestamps.from(payCashback.getOrderDate()))", target = "regDt")
  @Mapping(expression = "java(PaybackTimestamps.from(payCashback.getOrderDate()))", target = "chgDt")
  @Mapping(source = "payCashback.member.buyerNo", target = "regId")
  @Mapping(source = "payCashback.member.buyerNo", target = "chgId")
  @Mapping(expression = "java(smileCardCashback.toShopType().getCode())", target = "itemType")
  @Mapping(expression = "java(smileCardCashback.getSmileCardType().getCode())", target = "smileCardType")
  SmilecardCashbackOrderEntity map(PayCashback payCashback , SmileCardCashback smileCardCashback);
}
