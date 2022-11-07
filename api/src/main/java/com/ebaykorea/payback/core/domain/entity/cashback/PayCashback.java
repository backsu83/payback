package com.ebaykorea.payback.core.domain.entity.cashback;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class PayCashback {
  private final String txKey;
  private final long packNo;
  private final OrderSiteType orderSiteType;
  private final Instant orderDate;
  private final Member member;

  private final List<Cashback> cashbacks;

  public static PayCashback of(
      final String txKey,
      final long packNo,
      final OrderSiteType orderSiteType,
      final Instant orderDate,
      final Member member,
      final List<Cashback> cashbacks
  ) {
    return new PayCashback(txKey, packNo, orderSiteType, orderDate, member, cashbacks);
  }

  private PayCashback(
      final String txKey,
      final long packNo,
      final OrderSiteType orderSiteType,
      final Instant orderDate,
      final Member member,
      final List<Cashback> cashbacks
  ) {
    this.txKey = txKey;
    this.packNo = packNo;
    this.orderSiteType = orderSiteType;
    this.orderDate = orderDate;
    this.member = member;
    this.cashbacks = cashbacks;

    validate();
  }

  //불변식
  private void validate() {

  }

}
