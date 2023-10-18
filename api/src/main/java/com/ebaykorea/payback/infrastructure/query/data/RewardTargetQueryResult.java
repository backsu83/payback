package com.ebaykorea.payback.infrastructure.query.data;

import com.ebaykorea.payback.core.exception.PaybackException;
import lombok.Getter;
import lombok.Value;

import java.util.List;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_001;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;

@Getter
public class RewardTargetQueryResult {
  private final SmileCardQueryData smileCard;
  private final SsgPointTargetQueryData ssgPoint;
  private final List<CashbackOrderQueryData> cashbackOrders;

  public static final RewardTargetQueryResult EMPTY = RewardTargetQueryResult.of(SmileCardQueryData.EMPTY, SsgPointTargetQueryData.EMPTY, emptyList());

  public static RewardTargetQueryResult of(
      final SmileCardQueryData smileCard,
      final SsgPointTargetQueryData ssgPoint,
      final List<CashbackOrderQueryData> cashbackOrders) {
    return new RewardTargetQueryResult(smileCard, ssgPoint, cashbackOrders);
  }

  private RewardTargetQueryResult(
      final SmileCardQueryData smileCard,
      final SsgPointTargetQueryData ssgPoint,
      final List<CashbackOrderQueryData> cashbackOrders) {
    this.smileCard = smileCard;
    this.ssgPoint = ssgPoint;
    this.cashbackOrders = cashbackOrders;

    validate();
  }

  private void validate() {
    //cashbackUnits에 동일한 캐시백 타입이 두개 이상 존재 할 수 없다
    final var hasMoreThanOneCashbackType = cashbackOrders.stream()
        .collect(groupingBy(CashbackOrderQueryData::getCashbackType))
        .entrySet().stream()
        .anyMatch(entry -> entry.getValue().size() > 1);
    if (hasMoreThanOneCashbackType) {
      throw new PaybackException(DOMAIN_ENTITY_001 , "SavedCashbackQueryResult");
    }
  }
}
