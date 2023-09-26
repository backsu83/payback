package com.ebaykorea.payback.core.gateway;

import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.order.OrderUnitKey;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import com.ebaykorea.payback.core.domain.entity.reward.RewardSsgPointPolicy;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardResponseDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RewardGateway {

  RewardCashbackPolicies getCashbackPolicies(
      Order order,
      Payment payment,
      Map<String, ItemSnapshot> itemSnapshotMap,
      Map<String, OrderUnitKey> orderUnitKeyMap);

  Optional<MemberEventRewardResponseDto> saveEventCashback(String memberKey, List<MemberEventRewardRequestDto> requests);

}
