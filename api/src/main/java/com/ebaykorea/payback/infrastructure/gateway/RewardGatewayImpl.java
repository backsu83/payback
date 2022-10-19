package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.reward.RewardBackendCashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicy;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.CashbackRewardBackendResponseDto;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.CashbackRewardRequestDto;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.CashbackRewardResponseDto;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardBaseResponse;
import com.ebaykorea.payback.infrastructure.mapper.RewardGatewayMapper;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.RewardApiClient;
import com.ebaykorea.payback.util.PaybackInstants;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RewardGatewayImpl implements RewardGateway {

  private final RewardApiClient rewardApiClient;
  private final RewardGatewayMapper rewardGatewayMapper;

  @Override
  public RewardCashbackPolicies findCashbackPolicies(final Order order,
      final Map<String, ItemSnapshot> itemSnapshotMap) {
    final var request = toCashbackRewardRequestDto(order, itemSnapshotMap);

    final var cashbackRewardResponse = rewardApiClient.getCashbackReward(request)
        .map(RewardBaseResponse::findSuccessData)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .orElseThrow(() -> new RuntimeException("")); //TODO: Exception

    final var cashbackRewardBackendsResponse = rewardApiClient.getCashbackRewardBackend(request)
        .map(RewardBaseResponse::findSuccessData)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .orElseThrow(() -> new RuntimeException("")); //TODO: Exception

    return RewardCashbackPolicies.of(
        toRewardCashbackPolicies(cashbackRewardResponse),
        toRewardBackendCashbackPolicies(cashbackRewardBackendsResponse),
        PaybackInstants.from(cashbackRewardResponse.getUseEnableDate()),
        BigDecimal.valueOf(cashbackRewardResponse.getIfSmileCardCashbackAmount()),
        BigDecimal.valueOf(cashbackRewardResponse.getIfNewSmileCardCashbackAmount()));
  }

  CashbackRewardRequestDto toCashbackRewardRequestDto(final Order order,
      final Map<String, ItemSnapshot> itemSnapshotMap) {
    return CashbackRewardRequestDto.builder()
        //.totalPrice() //TODO: 주결제수단 금액 payment-api 결과 필요
        .goods(buildGoods(order, itemSnapshotMap))
        .build();
  }

  List<CashbackRewardRequestDto.Goods> buildGoods(final Order order,
      final Map<String, ItemSnapshot> itemSnapshotMap) {
    final var bundleDiscountMap = order.getBundleDiscountMap();

    return order.getOrderUnits().stream()
        .map(orderUnit ->
            rewardGatewayMapper.map(
                order.getBuyer(),
                orderUnit,
                itemSnapshotMap.get(orderUnit.getOrderItem().getItemSnapshotKey()),
                bundleDiscountMap.get(orderUnit.getOrderUnitKey())))
        .collect(Collectors.toUnmodifiableList());
  }

  List<RewardCashbackPolicy> toRewardCashbackPolicies(
      final CashbackRewardResponseDto cashbackRewardResponse) {
    return cashbackRewardResponse.getGoods().stream()
        .flatMap(good -> good.getCashbackInfo().stream()
            .map(cashbackInfo -> rewardGatewayMapper.map(good, cashbackInfo)))
        .collect(Collectors.toUnmodifiableList());
  }

  List<RewardBackendCashbackPolicy> toRewardBackendCashbackPolicies(
      final List<CashbackRewardBackendResponseDto> cashbackRewardBackendsResponse) {
    return cashbackRewardBackendsResponse.stream()
        .map(rewardGatewayMapper::map)
        .collect(Collectors.toUnmodifiableList());
  }
}
