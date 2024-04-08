package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.order.OrderUnitKey;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardPolicy;
import com.ebaykorea.payback.core.gateway.RewardPolicyGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.RewardPolicyApiClient;
import com.ebaykorea.payback.infrastructure.gateway.mapper.RewardPolicyGatewayMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RewardPolicyGatewayImpl implements RewardPolicyGateway {

  private final RewardPolicyApiClient client;
  private final RewardPolicyGatewayMapper mapper;

  @Override
  public RewardPolicy getRewardPolicies(
      final Order order,
      final Payment payment,
      final Map<String, ItemSnapshot> itemSnapshotMap,
      final Map<String, OrderUnitKey> orderUnitKeyMap) {
    final var request = mapper.map(order, payment, itemSnapshotMap, orderUnitKeyMap);

    return client.findRewardPolicy(request)
        .map(mapper::map)
        .orElse(RewardPolicy.EMPTY);
  }
}
