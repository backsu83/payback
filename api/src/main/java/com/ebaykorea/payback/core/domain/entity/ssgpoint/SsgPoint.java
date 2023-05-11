package com.ebaykorea.payback.core.domain.entity.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SsgPoint
{
  Long packNo;
  String buyerNo;
  Boolean isMember;
  Instant orderDate;
  OrderSiteType orderSiteType;
  List<SsgPointUnit> ssgPointUnits;

  private SsgPoint(final long packNo,
      final String buyerNo,
      final Boolean isMember,
      final Instant orderDate,
      final OrderSiteType orderSiteType,
      final List<SsgPointUnit> ssgPointUnits
  ) {
    this.packNo = packNo;
    this.buyerNo = buyerNo;
    this.isMember = isMember;
    this.orderDate = orderDate;
    this.orderSiteType = orderSiteType;
    this.ssgPointUnits = ssgPointUnits;
  }

  public static SsgPoint of(long packNo,
      final String buyerNo,
      final boolean isMember,
      final Instant orderDate,
      final OrderSiteType orderSiteType,
      final List<SsgPointUnit> ssgPointUnits
  ) {
    return new SsgPoint(packNo, buyerNo, isMember, orderDate, orderSiteType, ssgPointUnits);
  }

}
