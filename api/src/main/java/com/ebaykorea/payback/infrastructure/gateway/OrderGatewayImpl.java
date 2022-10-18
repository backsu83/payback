package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.gateway.OrderGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.order.OrderApiClient;
import com.ebaykorea.payback.infrastructure.mapper.OrderGatewayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderGatewayImpl implements OrderGateway {
  private final OrderApiClient orderApiClient;
  private final OrderGatewayMapper orderGatewayMapper;

  private static final String ORDER_QUERY_FIELDS = "orderKey,paySeq,orderBase,orderUnits,buyer,bundleDiscounts,tenant";

  @Override
  public Optional<Order> findOrder(final String orderKey, final String userKey) {
    return orderApiClient.findOrder(orderKey, ORDER_QUERY_FIELDS)
        .map(order -> orderGatewayMapper.map(order, userKey));
  }

  //TODO 주문시점의 상품 스냅샷 조회
}
