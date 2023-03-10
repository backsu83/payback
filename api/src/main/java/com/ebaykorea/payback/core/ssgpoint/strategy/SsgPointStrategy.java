package com.ebaykorea.payback.core.ssgpoint.strategy;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;

public interface SsgPointStrategy {

  OrderSiteType getSiteType();

  /**
   * SSG 포인트 저장
   * 상태값 PointStatusType.Ready
   * @param ssgPoint
   */
  SsgPoint save(SsgPoint ssgPoint);

  /**
   * SSG 포인트 취소
   * 상태값 PointStatusType.Cancel
   */
  void cancel(SsgPoint ssgPoint);

  /**
   * 포인트 사용
   * 상태값 미정
   */
  void use(SsgPoint ssgPoint);

}
