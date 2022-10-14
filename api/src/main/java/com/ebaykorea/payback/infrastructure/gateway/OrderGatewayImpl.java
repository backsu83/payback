package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.gateway.OrderGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.order.OrderApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderGatewayImpl implements OrderGateway {
  private final OrderApiClient orderApiClient;
  private static final String ORDER_QUERY_FIELDS = "orderKey,txKey,paySeq,orderBase,orderUnits,buyer,bundleDiscounts,tenant";

  //TODO: 주문 조회 구현
}
