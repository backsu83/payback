package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.order.OrderUnitKey;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardBackendCashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicy;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.*;
import com.ebaykorea.payback.infrastructure.gateway.mapper.RewardGatewayMapper;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.RewardApiClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.GATEWAY_002;
import static com.ebaykorea.payback.util.PaybackNumbers.toInteger;

@Service
@RequiredArgsConstructor
public class RewardGatewayImpl implements RewardGateway {

  private final RewardApiClient rewardApiClient;
  private final RewardGatewayMapper rewardGatewayMapper;

  @Override
  public RewardCashbackPolicies getCashbackPolicies(
      final Order order,
      final Payment payment,
      final Map<String, ItemSnapshot> itemSnapshotMap,
      final Map<String, OrderUnitKey> orderUnitKeyMap) {
    final var request = toCashbackRewardRequestDto(order, payment, itemSnapshotMap, orderUnitKeyMap);

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
            .orElseThrow(() -> new PaybackException(GATEWAY_002, "getCashbackReward 실패")));
  }

  private CompletableFuture<List<CashbackRewardBackendResponseDto>> getCashbackBackendRewardAsync(final CashbackRewardRequestDto request) {
    return CompletableFuture.supplyAsync(() -> rewardApiClient.getCashbackRewardBackend(request))
        .thenApply(a -> a.map(RewardBaseResponse::findSuccessData)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .orElseThrow(() -> new PaybackException(GATEWAY_002, "getCashbackRewardBackend 실패")));
  }


  CashbackRewardRequestDto toCashbackRewardRequestDto(
      final Order order,
      final Payment payment,
      final Map<String, ItemSnapshot> itemSnapshotMap,
      final Map<String, OrderUnitKey> orderUnitKeyMap
  ) {
    return new CashbackRewardRequestDto(
        toInteger(payment.getMainPaymentAmount()),
        buildGoods(order, itemSnapshotMap, orderUnitKeyMap)
    );
  }

  List<CashbackRewardGoodRequestDto> buildGoods(
      final Order order,
      final Map<String, ItemSnapshot> itemSnapshotMap,
      final Map<String, OrderUnitKey> orderUnitKeyMap
  ) {
    final var bundleDiscountMap = order.findBundleDiscountMap();

    return order.getOrderUnits().stream()
        .map(orderUnit ->
            rewardGatewayMapper.map(
                order.getBuyer(),
                orderUnit,
                orderUnitKeyMap.get(orderUnit.getOrderUnitKey()),
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
