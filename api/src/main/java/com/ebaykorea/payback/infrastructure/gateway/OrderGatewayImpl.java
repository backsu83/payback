package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshots;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.gateway.OrderGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.order.OrderApiClient;
import com.ebaykorea.payback.infrastructure.gateway.mapper.OrderGatewayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ebaykorea.payback.core.domain.constant.PaybackMessageType.API_GATEWAY_002;

@Service
@RequiredArgsConstructor
public class OrderGatewayImpl implements OrderGateway {
  private final OrderApiClient orderApiClient;
  private final OrderGatewayMapper orderGatewayMapper;

  private static final String ORDER_QUERY_FIELDS = "orderKey,paySeq,orderBase,orderUnits,buyer,bundleDiscounts";

  @Override
  public Order getOrder(final String orderKey) {
    return orderApiClient.findOrder(orderKey, ORDER_QUERY_FIELDS)
        .map(orderGatewayMapper::map)
        .orElseThrow(() -> new PaybackException(API_GATEWAY_002, "주문정보 결과 없음"));
  }

  @Override
  public ItemSnapshots getItemSnapshot(final List<String> itemSnapshotKeys) {
    final var itemSnapshots = orderApiClient.findItemSnapshots(itemSnapshotKeys).stream()
        .map(orderGatewayMapper::map)
        .collect(Collectors.toUnmodifiableList());

    return ItemSnapshots.of(itemSnapshots);
  }
}
