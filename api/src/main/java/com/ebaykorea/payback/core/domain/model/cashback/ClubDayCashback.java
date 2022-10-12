package com.ebaykorea.payback.core.domain.model.cashback;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import com.ebaykorea.payback.core.domain.constant.ShopType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClubDayCashback extends Cashback {

  public ClubDayCashback(
      final long orderNo,
      final String itemNo,
      final CashbackType type,
      final ShopType shopType,
      final BigDecimal amount,
      final BigDecimal basisAmount,
      final Instant useEnableDate,
      final boolean isClubMember
  ) {
    super(
        orderNo,
        itemNo,
        type,
        shopType,
        amount,
        basisAmount,
        useEnableDate,
        CashbackApplyStrategy.clubDayCashbackStrategy(amount, isClubMember));
  }
}
