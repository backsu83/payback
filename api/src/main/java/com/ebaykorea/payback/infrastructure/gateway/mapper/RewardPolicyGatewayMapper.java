package com.ebaykorea.payback.infrastructure.gateway.mapper;

import com.ebaykorea.payback.core.domain.constant.ShopType;
import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.order.OrderUnit;
import com.ebaykorea.payback.core.domain.entity.order.OrderUnitKey;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardPolicy;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardPolicyDetailRequestDto;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardPolicyDto;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardPolicyRequestDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RewardPolicyGatewayMapper {

  default RewardPolicyRequestDto map(
      final Order order,
      final Payment payment,
      final Map<String, ItemSnapshot> itemSnapshotMap,
      final Map<String, OrderUnitKey> orderUnitKeyMap) {
    return RewardPolicyRequestDto.builder()
        .totalPrice(payment.getMainPaymentAmount())
        .club(order.getBuyer().isSmileClubMember())
        .details(buildDetailRequests(order, itemSnapshotMap, orderUnitKeyMap))
        .build();
  }

  default List<RewardPolicyDetailRequestDto> buildDetailRequests(
      final Order order,
      final Map<String, ItemSnapshot> itemSnapshotMap,
      final Map<String, OrderUnitKey> orderUnitKeyMap) {
    return order.getOrderUnits().stream()
        .map(orderUnit -> map(
            orderUnit,
            orderUnitKeyMap.get(orderUnit.getOrderUnitKey()),
            itemSnapshotMap.get(orderUnit.getOrderItem().getItemSnapshotKey()),
            order.getBundleDiscountPrice(orderUnit.getOrderUnitKey()),
            order.getExtraDiscountPrice(orderUnit.getOrderUnitKey())))
        .collect(Collectors.toUnmodifiableList());
  }

  @Mapping(source = "orderUnitKey.buyOrderNo", target = "key")
  @Mapping(source = "itemSnapshot.itemNo", target = "itemNo")
  @Mapping(source = "itemSnapshot.itemLargeCategoryCode", target = "largeCategoryCode")
  @Mapping(source = "itemSnapshot.itemMediumCategoryCode", target = "mediumCategoryCode")
  @Mapping(source = "itemSnapshot.itemSmallCategoryCode", target = "smallCategoryCode")
  @Mapping(source = "itemSnapshot.sellerCustNo", target = "sellerKey")
  @Mapping(source = "itemSnapshot", target = "itemType", qualifiedByName = "mapItemType")
  @Mapping(source = "orderUnit.orderItem.quantity", target = "qty")
  @Mapping(expression = "java(orderUnit.orderUnitPrice(bundleDiscountPrice, extraDiscountPrice))", target = "price")
  @Mapping(source = "itemSnapshot.moneyCategory", target = "redeemable")
  RewardPolicyDetailRequestDto map(
      OrderUnit orderUnit,
      OrderUnitKey orderUnitKey,
      ItemSnapshot itemSnapshot,
      BigDecimal bundleDiscountPrice,
      BigDecimal extraDiscountPrice);

  @Named("mapItemType")
  default String mapItemType(final ItemSnapshot itemSnapshot) {
    if (itemSnapshot.isSmileDelivery()) {
      return ShopType.SmileDelivery.getCode();
    } else if (itemSnapshot.isSmileFresh()) {
      return ShopType.SmileFresh.getCode();
    } else {
      return "";
    }
  }

  RewardPolicy map(RewardPolicyDto source);
}
