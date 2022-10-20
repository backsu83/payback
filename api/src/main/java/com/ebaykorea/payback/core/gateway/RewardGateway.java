package com.ebaykorea.payback.core.gateway;

import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import java.util.Map;

public interface RewardGateway {

  RewardCashbackPolicies findCashbackPolicies(Order order, Map<String, ItemSnapshot> itemSnapshotMap);
}
