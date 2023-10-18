package com.ebaykorea.payback.infrastructure.query.data;

import com.ebaykorea.payback.core.exception.PaybackException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import java.util.List;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_001;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;

@Getter
@EqualsAndHashCode
public class RewardTargetQueryResult {
  @Schema(description = "스마일카드 적립")
  SmileCardQueryData smileCard;
  @Schema(description = "SSGPOINT 적립")
  SsgPointTargetQueryData ssgPoint;
  @Schema(description = "캐시백 적립 대상")
  List<CashbackTargetQueryData> cashbackTargets;

  public static final RewardTargetQueryResult EMPTY = RewardTargetQueryResult.of(SmileCardQueryData.EMPTY, SsgPointTargetQueryData.EMPTY, emptyList());

  public static RewardTargetQueryResult of(
      final SmileCardQueryData smileCard,
      final SsgPointTargetQueryData ssgPoint,
      final List<CashbackTargetQueryData> cashbackTargets) {
    return new RewardTargetQueryResult(smileCard, ssgPoint, cashbackTargets);
  }

  private RewardTargetQueryResult(
      final SmileCardQueryData smileCard,
      final SsgPointTargetQueryData ssgPoint,
      final List<CashbackTargetQueryData> cashbackTargets) {
    this.smileCard = smileCard;
    this.ssgPoint = ssgPoint;
    this.cashbackTargets = cashbackTargets;

    validate();
  }

  private void validate() {
    //cashbackTargets 에 동일한 캐시백 타입이 두개 이상 존재 할 수 없다
    final var hasMoreThanOneCashbackType = cashbackTargets.stream()
        .collect(groupingBy(CashbackTargetQueryData::getCashbackType))
        .entrySet().stream()
        .anyMatch(entry -> entry.getValue().size() > 1);
    if (hasMoreThanOneCashbackType) {
      throw new PaybackException(DOMAIN_ENTITY_001 , "SavedCashbackQueryResult");
    }
  }
}
