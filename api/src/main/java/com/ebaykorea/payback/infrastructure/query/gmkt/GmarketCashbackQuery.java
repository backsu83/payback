package com.ebaykorea.payback.infrastructure.query.gmkt;

import com.ebaykorea.payback.config.properties.SaturnApplicationProperties;
import com.ebaykorea.payback.infrastructure.gateway.TransactionGatewayImpl;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.CashbackOrderRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.SmilecardCashbackOrderRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.CashbackOrderEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmilecardCashbackOrderEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.ssgpoint.SsgPointTargetRepository;
import com.ebaykorea.payback.infrastructure.query.CashbackQuery;
import com.ebaykorea.payback.infrastructure.query.data.*;
import com.ebaykorea.payback.infrastructure.query.mapper.RewardTargetQueryMapper;
import com.ebaykorea.payback.util.PaybackInstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;
import static com.ebaykorea.payback.util.PaybackDecimals.summarizing;
import static com.ebaykorea.payback.util.PaybackStrings.isBlank;
import static com.ebaykorea.payback.util.PaybackStrings.orEmpty;
import static com.ebaykorea.payback.util.support.MDCDecorator.withMdc;
import static java.util.stream.Collectors.*;

@Profile(GMARKET_TENANT)
@Service
@RequiredArgsConstructor
public class GmarketCashbackQuery implements CashbackQuery {

  private final TransactionGatewayImpl transactionGateway;

  private final SsgPointTargetRepository ssgPointTargetRepository;
  private final SmilecardCashbackOrderRepository smilecardCashbackOrderRepository;
  private final CashbackOrderRepository cashbackOrderRepository;

  private final RewardTargetQueryMapper rewardTargetQueryMapper;

  private final SaturnApplicationProperties properties;

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

    return RewardTargetQueryResult.of(smileCard, targetedSsgPointUnits, cashbackOrders);
  }

  private CompletableFuture<SmileCardQueryData> findSmileCardAsync(final long packNo) {
    return CompletableFuture.supplyAsync(withMdc(() -> smilecardCashbackOrderRepository.findById(packNo)))
        .thenApply(entity -> entity
            .filter(SmilecardCashbackOrderEntity::hasCashbackAmount)
            .map(rewardTargetQueryMapper::map)
            .orElse(null));
  }

  private CompletableFuture<List<SsgPointTargetQueryData>> findTargetedSsgPointUnitsAsync(final long packNo) {
    return CompletableFuture.supplyAsync(withMdc(() -> ssgPointTargetRepository.findByPackNo(packNo)))
        .thenApply(entities -> entities.stream()
            .filter(entity -> properties.getSiteCode().equalsIgnoreCase(orEmpty(entity.getSiteType())))
            .map(rewardTargetQueryMapper::map)
            .filter(SsgPointTargetUnitQueryData::isTarget)
            .collect(groupingBy( //ssgPoint 적립 대상건들을 적립예정일 별 금액 sum 으로 map (Map<Instant, BigDecimal)
                SsgPointTargetUnitQueryData::getExpectSaveDate,
                mapping(SsgPointTargetUnitQueryData::getSaveAmount, summarizing())))
            .entrySet().stream()
            .map(targetedSsgPointUnits -> SsgPointTargetQueryData.of(targetedSsgPointUnits.getKey(), targetedSsgPointUnits.getValue()))
            .collect(toUnmodifiableList()));
  }

  private CompletableFuture<List<CashbackTargetQueryData>> findTargetedCashbackOrdersAsync(final long packNo) {
    return CompletableFuture.supplyAsync(withMdc(() -> cashbackOrderRepository.findByPackNo(packNo)))
        .thenApply(entities -> entities.stream()
            .filter(CashbackOrderEntity::isTarget)
            .collect(groupingBy(CashbackOrderEntity::getCashbackType)).entrySet().stream()
            .map(entry -> rewardTargetQueryMapper.map(entry.getKey(), totalCashbackOrderSaveAmount(entry.getValue()), findExpectSaveDate(entry.getValue())))
            .collect(toUnmodifiableList()));
  }

  private BigDecimal totalCashbackOrderSaveAmount(final List<CashbackOrderEntity> entities) {
    return entities.stream()
        .map(CashbackOrderEntity::getAmount)
        .collect(summarizing());
  }

  private Instant findExpectSaveDate(final List<CashbackOrderEntity> entities) {
    return entities.stream()
        .map(CashbackOrderEntity::getUseEnableDt)
        .map(PaybackInstants::from)
        .findAny()
        .orElse(null);
  }
}
