package com.ebaykorea.payback.core.domain.entity.cashback;

import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import com.ebaykorea.payback.core.domain.entity.cashback.smilecard.SmileCardCashback;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Getter
@EqualsAndHashCode
@ToString
public class PayCashback {

  private final long packNo;
  private final Instant orderDate;
  private final Member member;

  private final List<Cashback> cashbacks;
  private final SmileCardCashback smileCardCashback;

  public static PayCashback of(
      final long packNo,
      final Instant orderDate,
      final Member member,
      final List<Cashback> cashbacks,
      final SmileCardCashback smileCardCashback
  ) {
    return new PayCashback(packNo, orderDate, member, cashbacks, smileCardCashback);
  }

  private PayCashback(
      final long packNo,
      final Instant orderDate,
      final Member member,
      final List<Cashback> cashbacks,
      final SmileCardCashback smileCardCashback
  ) {
    this.packNo = packNo;
    this.orderDate = orderDate;
    this.member = member;
    this.cashbacks = cashbacks;
    this.smileCardCashback = smileCardCashback;

    validate();
  }

  //불변식
  private void validate() {

  }

  // 적용여부와 관계없이 스마일카드 캐시백 금액이 있는지 여부
  public boolean hasSmileCardCashbackAmount() {
    return Optional.ofNullable(smileCardCashback)
        .map(SmileCardCashback::hasSmileCardCashbackAmount)
        .orElse(false);
  }

  // 적용 가능한 스마일카드 추가 캐시백 여부
  public boolean hasSmileCardAdditionalCashbacks() {
    return Optional.ofNullable(smileCardCashback)
        .map(SmileCardCashback::hasAdditionalCashback)
        .orElse(false);
  }
}
