package com.ebaykorea.payback.core.domain.entity.cashback;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import com.ebaykorea.payback.core.domain.entity.cashback.policy.CashbackPolicy;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class PayCashback {
  private final String orderKey;
  private final long packNo;
  private final OrderSiteType orderSiteType;
  private final Instant orderDate;
  private final Member member;

  //TODO: detail에 저장 시 어떻게 할지 고민
  private final List<Cashback> cashbacks;

  /**
   * 실제 캐시백 적립여부와 관계 없이 독립적으로 저장되어야 하기 때문에 cashback내에 두지 않음
   * TODO: 캐시백 적립 여부에 따라 정책 정보도 함께 저장하는것이 좋을것 같은데 협의 후 결정 필요
   */
  private final List<CashbackPolicy> cashbackPolicies;


  public static PayCashback of(
      final String orderKey,
      final long packNo,
      final OrderSiteType orderSiteType,
      final Instant orderDate,
      final Member member,
      final List<Cashback> cashbacks,
      final List<CashbackPolicy> cashbackPolicies
  ) {
    return new PayCashback(orderKey, packNo, orderSiteType, orderDate, member, cashbacks, cashbackPolicies);
  }

  private PayCashback(
      final String orderKey,
      final long packNo,
      final OrderSiteType orderSiteType,
      final Instant orderDate,
      final Member member,
      final List<Cashback> cashbacks,
      final List<CashbackPolicy> cashbackPolicies
  ) {
    this.orderKey = orderKey;
    this.packNo = packNo;
    this.orderSiteType = orderSiteType;
    this.orderDate = orderDate;
    this.member = member;
    this.cashbacks = cashbacks;
    this.cashbackPolicies = cashbackPolicies;

    validate();
  }

  //불변식
  private void validate() {

  }

}
