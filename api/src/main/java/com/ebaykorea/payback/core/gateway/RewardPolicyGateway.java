package com.ebaykorea.payback.core.gateway;

import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.order.OrderUnitKey;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardPolicy;
import java.util.Map;

public interface RewardPolicyGateway {
  RewardPolicy getRewardPolicies(
      Order order,
      Payment payment,
      Map<String, ItemSnapshot> itemSnapshotMap,
      Map<String, OrderUnitKey> orderUnitKeyMap);
}
