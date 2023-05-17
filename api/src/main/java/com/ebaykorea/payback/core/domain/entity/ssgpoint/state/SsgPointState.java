package com.ebaykorea.payback.core.domain.entity.ssgpoint.state;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointStatus;

public interface SsgPointState {

  /**
   * SSG 포인트 저장 상태값 PointStatusType.Ready
   */
  default SsgPointStatus ready() {
    return SsgPointStatus.builder()
        .statusType(PointStatusType.Ready)
        .tradeType(PointTradeType.Save)
        .build();
  }

  /**
   * SSG 포인트 취소
   * 상태값 PointStatusType.Cancel
   */
  default SsgPointStatus cancel() {
    return SsgPointStatus.builder()
        .statusType(PointStatusType.Ready)
        .tradeType(PointTradeType.Cancel)
        .build();
  }

  PointStatusType cancelStatus(String pointStatusType, String scheduleDate);

  /**
   * SSG 포인트 보류
   * 상태값 PointStatusType.WithHold
   */
  default SsgPointStatus cancelBeforeSave() {
    return SsgPointStatus.builder()
        .statusType(PointStatusType.CancelBeforeSave)
        .tradeType(PointTradeType.Save)
        .build();
  }
}
