package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.gateway.TransactionGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.transaction.TransactionApiClient;
import com.ebaykorea.payback.infrastructure.mapper.TransactionGatewayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.GATEWAY_002;


@Service
@RequiredArgsConstructor
public class TransactionGatewayImpl implements TransactionGateway {

  private final TransactionApiClient transactionApiClient;
  private final TransactionGatewayMapper transactionGatewayMapper;

  @Override
  public KeyMap getKeyMap(final String orderKey) {
    final var keyMaps = transactionApiClient.findKeyMaps(orderKey);
    final var firstKeyMap = keyMaps.stream().findFirst()
        .orElseThrow(() -> new PaybackException(GATEWAY_002, "값 없음"));

    final var orderUnitKeys = keyMaps.stream()
        .map(transactionGatewayMapper::mapToOrderUnitKey)
        .collect(Collectors.toUnmodifiableList());

    return KeyMap.of(firstKeyMap.getOrderKey(), firstKeyMap.getPackNo(), orderUnitKeys);
  }
}
