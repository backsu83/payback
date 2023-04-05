package com.ebaykorea.payback.core.domain.entity.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SsgPoint
{
  Long packNo;
  String buyerId;
  Instant orderDate;
  OrderSiteType orderSiteType;
  List<SsgPointUnit> ssgPointUnits;

  private SsgPoint(final long packNo,
      final String buyerId,
      final Instant orderDate,
      final OrderSiteType orderSiteType,
      final List<SsgPointUnit> ssgPointUnits
  ) {
    this.packNo = packNo;
    this.buyerId = buyerId;
    this.orderDate = orderDate;
    this.orderSiteType = orderSiteType;
    this.ssgPointUnits = ssgPointUnits;
  }

  public static SsgPoint of(long packNo,
      final String buyerId,
      final Instant orderDate,
      final OrderSiteType orderSiteType,
      final List<SsgPointUnit> ssgPointUnits
  ) {
    return new SsgPoint(packNo, buyerId, orderDate, orderSiteType, ssgPointUnits);
  }

}
