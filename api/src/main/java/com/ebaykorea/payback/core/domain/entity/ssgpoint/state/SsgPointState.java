package com.ebaykorea.payback.core.domain.entity.ssgpoint.state;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointStatus;

public interface SsgPointState {

  OrderSiteType site();

  /**
   * SSG 포인트 저장
   * 상태값 PointStatusType.Ready
   */
  SsgPointStatus ready();

  /**
   * SSG 포인트 취소
   * 상태값 PointStatusType.Cancel
   */
  SsgPointStatus cancel();

  /**
   * SSG 포인트 보류
   * 상태값 PointStatusType.WithHold
   */
  default SsgPointStatus withhold() {
    return SsgPointStatus.builder()
        .statusType(PointStatusType.WithHold)
        .tradeType(PointTradeType.Save)
        .build();
  }
}
