package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshots;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.gateway.OrderGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.order.OrderApiClient;
import com.ebaykorea.payback.infrastructure.mapper.OrderGatewayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderGatewayImpl implements OrderGateway {
  private final OrderApiClient orderApiClient;
  private final OrderGatewayMapper orderGatewayMapper;

  private static final String ORDER_QUERY_FIELDS = "orderKey,paySeq,orderBase,orderUnits,buyer,bundleDiscounts,tenant";

  @Override
  public Optional<Order> findOrder(final String orderKey) {
    return orderApiClient.findOrder(orderKey, ORDER_QUERY_FIELDS)
        .map(orderGatewayMapper::map);
  }

  @Override
  public ItemSnapshots findItemSnapshot(final List<String> itemSnapshotKeys) {
    final var itemSnapshots = orderApiClient.findItemSnapshots(itemSnapshotKeys).stream()
        .map(orderGatewayMapper::map)
        .collect(Collectors.toUnmodifiableList());

    return ItemSnapshots.of(itemSnapshots);
  }
}
