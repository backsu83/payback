package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.reward.RewardBackendCashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicy;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.*;
import com.ebaykorea.payback.infrastructure.mapper.RewardGatewayMapper;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.RewardApiClient;
import com.ebaykorea.payback.util.PaybackInstants;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RewardGatewayImpl implements RewardGateway {

  private final RewardApiClient rewardApiClient;
  private final RewardGatewayMapper rewardGatewayMapper;

  @Override
  public RewardCashbackPolicies findCashbackPolicies(
      final Order order,
      final Map<String, ItemSnapshot> itemSnapshotMap) {
    final var request = toCashbackRewardRequestDto(order, itemSnapshotMap);

    final var cashbackRewardResponseFuture = getCashbackRewardAsync(request);
    final var cashbackRewardBackendsResponseFuture = getCashbackBackendRewardAsync(request);

    final var cashbackRewardResponse = cashbackRewardResponseFuture.join();
    final var cashbackRewardBackendsResponse = cashbackRewardBackendsResponseFuture.join();

    return RewardCashbackPolicies.of(
        toRewardCashbackPolicies(cashbackRewardResponse),
        toRewardBackendCashbackPolicies(cashbackRewardBackendsResponse),
        cashbackRewardResponse.getUseEnableDate(),
        BigDecimal.valueOf(cashbackRewardResponse.getIfSmileCardCashbackAmount()),
        BigDecimal.valueOf(cashbackRewardResponse.getIfNewSmileCardCashbackAmount()));
  }

  private CompletableFuture<CashbackRewardResponseDto> getCashbackRewardAsync(final CashbackRewardRequestDto request) {
    return CompletableFuture.supplyAsync(() -> rewardApiClient.getCashbackReward(request))
        .thenApply(a -> a.map(RewardBaseResponse::findSuccessData)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .orElseThrow(() -> new RuntimeException(""))); //TODO: Exception
  }

  private CompletableFuture<List<CashbackRewardBackendResponseDto>> getCashbackBackendRewardAsync(final CashbackRewardRequestDto request) {
    return CompletableFuture.supplyAsync(() -> rewardApiClient.getCashbackRewardBackend(request))
        .thenApply(a -> a.map(RewardBaseResponse::findSuccessData)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .orElseThrow(() -> new RuntimeException(""))); //TODO: Exception
  }


  CashbackRewardRequestDto toCashbackRewardRequestDto(
      final Order order,
      final Map<String, ItemSnapshot> itemSnapshotMap) {
    return new CashbackRewardRequestDto(
        0, //TODO: 주결제수단 금액 payment-api 결과 필요
        buildGoods(order, itemSnapshotMap)
    );
  }

  List<CashbackRewardGoodRequestDto> buildGoods(
      final Order order,
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
