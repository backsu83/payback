package com.ebaykorea.payback.infrastructure.gateway.mapper;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.cashback.smilecard.T2T3SmileCardCashback;
import com.ebaykorea.payback.core.domain.entity.order.Buyer;
import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.OrderUnit;
import com.ebaykorea.payback.core.domain.entity.order.OrderUnitKey;
import com.ebaykorea.payback.core.domain.entity.reward.RewardBackendCashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.reward.RewardT2T3SmileCardCashbackPolicy;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.*;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.T2T3Cashback;
import com.ebaykorea.payback.util.PaybackBooleans;
import com.ebaykorea.payback.util.PaybackNumbers;
import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = "spring", imports = {PaybackBooleans.class, PaybackNumbers.class})
public interface RewardGatewayMapper {

  @Mapping(source = "orderUnitKey.buyOrderNo", target = "key")
  @Mapping(constant = "0", target = "siteCd")
  @Mapping(source = "itemSnapshot.itemNo", target = "gdNo")
  @Mapping(source = "itemSnapshot.itemLargeCategoryCode", target = "gdlcCd")
  @Mapping(source = "itemSnapshot.itemMediumCategoryCode", target = "gdmcCd")
  @Mapping(source = "itemSnapshot.itemSmallCategoryCode", target = "gdscCd")
  @Mapping(source = "itemSnapshot.sellerCustNo", target = "scNo")
  @Mapping(source = "buyer.smileClubMember", target = "isSmileClub")
  @Mapping(source = "itemSnapshot.smileDelivery", target = "isSmileDelivery")
  @Mapping(source = "itemSnapshot.smileFresh", target = "isSmileFresh")
  @Mapping(source = "orderUnit.orderItem.quantity", target = "qty")
  @Mapping(expression = "java(PaybackNumbers.toInteger(orderUnit.orderUnitPrice(bundleDiscountPrice)))", target = "price")
  @Mapping(expression = "java(PaybackBooleans.toYN(itemSnapshot.isMoneyCategory()))", target = "marketabilityItemYn")
  CashbackRewardGoodRequestDto map(Buyer buyer, OrderUnit orderUnit, OrderUnitKey orderUnitKey, ItemSnapshot itemSnapshot, BigDecimal bundleDiscountPrice);


  @Mapping(expression = "java(Long.valueOf(goods.getKey()))", target = "policyKey")
  @Mapping(source = "nspCashbackInfo.clubAmount", target = "clubCashbackAmount")
  RewardCashbackPolicy map(CashbackRewardGoodResponseDto goods, CashbackInfoDto cashbackInfo, NspCashbackInfoDto nspCashbackInfo);

  @Mapping(expression = "java(Long.valueOf(source.getKey()))", target = "policyKey")
  RewardBackendCashbackPolicy map(CashbackRewardBackendResponseDto source);

  @Mapping(expression = "java(Long.valueOf(source.getKey()))", target = "policyKey")
  @Mapping(source = "ifSmileCardT2T3CashbackAmount", target = "cashbackAmount")
  RewardT2T3SmileCardCashbackPolicy map(CashbackRewardGoodResponseDto source);

  @Mapping(expression = "java(mapToOrderSiteType())", target = "siteCode")
  @Mapping(source = "keyMap.packNo", target = "packNo")
  @Mapping(source = "t2T3SmileCard.orderNo", target = "buyOrderNo")
  @Mapping(source = "t2T3SmileCard.smileCardType", target = "smileCardType")
  @Mapping(source = "t2T3SmileCard.basisAmount", target = "itemPrice")
  @Mapping(source = "t2T3SmileCard.amount", target = "t2T3CashbackAmnt")
  @Mapping(source = "t2T3SmileCard.shopType.code", target = "itemType")
  T2T3Cashback map(KeyMap keyMap , T2T3SmileCardCashback t2T3SmileCard);

  default OrderSiteType mapToOrderSiteType() {
    return OrderSiteType.Gmarket;
  }
}
