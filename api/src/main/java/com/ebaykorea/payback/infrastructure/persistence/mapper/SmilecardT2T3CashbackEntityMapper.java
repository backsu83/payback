package com.ebaykorea.payback.infrastructure.persistence.mapper;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.cashback.PayCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.smilecard.T2SmileCardCashback;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.SmilecardT2T3CashbackEntity;
import com.ebaykorea.payback.util.PaybackTimestamps;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {PaybackTimestamps.class}
)
public interface SmilecardT2T3CashbackEntityMapper {

  @Mapping(source = "t2SmileCardCashback.orderNo", target = "orderNo")
  @Mapping(source = "t2SmileCardCashback.basisAmount", target = "itemPrice")
  @Mapping(source = "t2SmileCardCashback.amount", target = "t2t3CashbackAmount")
  @Mapping(expression = "java(t2SmileCardCashback.getSmileCardType().getCode())", target = "smileCardType")
  @Mapping(expression = "java(mapToOrderSiteType())", target = "siteCode")
  @Mapping(source = "payCashback.member.buyerNo", target = "insOprt")
  @Mapping(source = "payCashback.member.buyerNo", target = "updOprt")
  @Mapping(expression = "java(PaybackTimestamps.from(payCashback.getOrderDate()))", target = "insDate")
  @Mapping(expression = "java(PaybackTimestamps.from(payCashback.getOrderDate()))", target = "updDate")
  @Mapping(expression = "java(t2SmileCardCashback.getShopType().getCode())", target = "itemType")
  SmilecardT2T3CashbackEntity map(PayCashback payCashback , T2SmileCardCashback t2SmileCardCashback);

  default String mapToOrderSiteType() {
   return OrderSiteType.Gmarket.getShortCode();
  }
}
