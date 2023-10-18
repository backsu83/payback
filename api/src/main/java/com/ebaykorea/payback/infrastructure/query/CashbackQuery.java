package com.ebaykorea.payback.infrastructure.query;

import com.ebaykorea.payback.infrastructure.gateway.TransactionGatewayImpl;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointTargetRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.SmilecardCashbackOrderRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderEntity;
import com.ebaykorea.payback.infrastructure.query.data.*;
import com.ebaykorea.payback.infrastructure.query.mapper.RewardTargetQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.ebaykorea.payback.util.PaybackStrings.isBlank;
import static com.ebaykorea.payback.util.support.MDCDecorator.withMdc;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class CashbackQuery {

  private final TransactionGatewayImpl transactionGateway;

  private final SsgPointTargetRepository ssgPointTargetRepository;
  private final SmilecardCashbackOrderRepository smilecardCashbackOrderRepository;
  private final CashbackOrderRepository cashbackOrderRepository;

  private final RewardTargetQueryMapper rewardTargetQueryMapper;

  public RewardTargetQueryResult getSavedCashback(final String txKey, final String orderKey) {
    if (!isBlank(txKey) && !isBlank(orderKey)) {
      final var keyMap = transactionGateway.getKeyMap(txKey, orderKey);
      return getSavedCashback(keyMap.getPackNo());
    }
    return RewardTargetQueryResult.EMPTY;
  }

  public RewardTargetQueryResult getSavedCashback(final Long packNo) {
    final var smileCardFuture = findSmileCardAsync(packNo);
    final var targetedSsgPointUnitFuture = findTargetedSsgPointUnitsAsync(packNo);
    final var targetedCashbackOrdersFuture = findTargetedCashbackOrdersAsync(packNo);

    final var smileCard = smileCardFuture.join();
    final var targetedSsgPointUnits = targetedSsgPointUnitFuture.join();
    final var cashbackOrders = targetedCashbackOrdersFuture.join();

    return RewardTargetQueryResult.of(smileCard, SsgPointTargetQueryData.of(targetedSsgPointUnits), cashbackOrders);
  }

  private CompletableFuture<SmileCardQueryData> findSmileCardAsync(final long packNo) {
    return CompletableFuture.supplyAsync(withMdc(() -> smilecardCashbackOrderRepository.findById(packNo)))
        .thenApply(entity -> entity.map(rewardTargetQueryMapper::map)
            .orElse(SmileCardQueryData.EMPTY));
  }

  private CompletableFuture<List<SsgPointTargetUnitQueryData>> findTargetedSsgPointUnitsAsync(final long packNo) {
    return CompletableFuture.supplyAsync(withMdc(() -> ssgPointTargetRepository.findByPackNo(packNo)))
        .thenApply(entities -> entities.stream()
            .map(rewardTargetQueryMapper::map)
            .filter(SsgPointTargetUnitQueryData::isTarget)
            .collect(toUnmodifiableList()));
  }

  private CompletableFuture<List<CashbackOrderQueryData>> findTargetedCashbackOrdersAsync(final long packNo) {
    return CompletableFuture.supplyAsync(withMdc(() -> cashbackOrderRepository.findByPackNo(packNo)))
        .thenApply(entities -> entities.stream()
            .filter(CashbackOrderEntity::isTarget)
            .map(rewardTargetQueryMapper::map)
            .collect(groupingBy(CashbackOrderQueryData::getCashbackType))
            .values().stream()
            .flatMap(Collection::stream)
            .collect(toUnmodifiableList()));
  }
}
