package com.ebaykorea.payback.core.domain.entity.cashback.unit;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import com.ebaykorea.payback.core.domain.constant.ShopType;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.CashbackPolicy;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SmilePayCashback extends CashbackUnit {

  private final BigDecimal clubAmount;
  private final BigDecimal nonClubAmount;

  public SmilePayCashback(
      final String itemNo,
      final ShopType shopType,
      final BigDecimal amount, //cashback_order와 detail.PAY_AMOUNT 저장하는 금액 소스가 다른데 통일할수 없는지 확인 필요
      final BigDecimal basisAmount,
      final Instant useEnableDate,
      final boolean isSmilePay,
      final List<CashbackPolicy> cashbackPolicies,
      final BigDecimal clubAmount,
      final BigDecimal nonClubAmount
      ) {
    super(
        itemNo,
        shopType,
        amount,
        basisAmount,
        useEnableDate,
        CashbackApplyStrategy.cashbackAvailableStrategy(amount, isSmilePay),
        cashbackPolicies);
    this.clubAmount = clubAmount;
    this.nonClubAmount = nonClubAmount;
  }

  @Override
  public CashbackType getCashbackType() {
    return CashbackType.SmilePay;
  }

  @Override
  public BigDecimal getClubAmount() {
    return clubAmount;
  }

  @Override
  public BigDecimal getNonClubAmount() {
    return nonClubAmount;
  }

}
