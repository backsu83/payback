package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.cashback.PayCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.smilecard.SmileCardAdditionalCashback;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmilecardT2T3CashbackEntity;
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

  @Mapping(source = "smileCardAdditionalCashback.orderNo", target = "orderNo")
  @Mapping(source = "smileCardAdditionalCashback.basisAmount", target = "itemPrice")
  @Mapping(source = "smileCardAdditionalCashback.amount", target = "t2t3CashbackAmount")
  @Mapping(expression = "java(smileCardAdditionalCashback.getSmileCardType().getCode())", target = "smileCardType")
  @Mapping(expression = "java(mapToOrderSiteType())", target = "siteCode")
  @Mapping(source = "payCashback.member.buyerNo", target = "insOprt")
  @Mapping(source = "payCashback.member.buyerNo", target = "updOprt")
  @Mapping(expression = "java(PaybackTimestamps.from(payCashback.getOrderDate()))", target = "insDate")
  @Mapping(expression = "java(PaybackTimestamps.from(payCashback.getOrderDate()))", target = "updDate")
  @Mapping(expression = "java(smileCardAdditionalCashback.getShopType().getCode())", target = "itemType")
  SmilecardT2T3CashbackEntity map(PayCashback payCashback , SmileCardAdditionalCashback smileCardAdditionalCashback);

  default String mapToOrderSiteType() {
   return OrderSiteType.Gmarket.getShortCode();
  }
}
