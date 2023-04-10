package com.ebaykorea.payback.core.ssgpoint.state;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointAuth;
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

}
