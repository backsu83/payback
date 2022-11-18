package com.ebaykorea.payback.core.gateway;

import com.ebaykorea.payback.core.domain.entity.cashback.smilecard.SmileCardCashback;
import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.order.OrderUnitKey;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.AddSmileCardT2T3CashbackRequestDto;
import java.util.Map;

public interface RewardGateway {

  RewardCashbackPolicies getCashbackPolicies(
      Order order,
      Payment payment,
      Map<String, ItemSnapshot> itemSnapshotMap,
      Map<String, OrderUnitKey> orderUnitKeyMap);

  void saveCardT2T3Cashback(KeyMap keyMap , SmileCardCashback smileCardCashback);
}
