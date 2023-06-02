package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward;


import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;

import static com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.QSsgPointTargetEntity.ssgPointTargetEntity;



@Repository
@RequiredArgsConstructor
public class SsgPointTargetRepositoryImpl implements SsgPointTargetRepositoryCustom {

  private final JPAQueryFactory factory;
  private final static String adminErrorCode = "ERR_ADMIN";

  @Override
  public long retryFailResponseCode(final Long tryCount,
      final String manualOprt,
      final Instant updateDate,
      final Long orderNo,
      final String buyerId,
      final String siteType,
      final String tradeType) {
    return factory.update(ssgPointTargetEntity)
        .set(ssgPointTargetEntity.responseCode , adminErrorCode)
        .set(ssgPointTargetEntity.tryCount , tryCount)
        .set(ssgPointTargetEntity.manualOprt , manualOprt)
        .set(ssgPointTargetEntity.updateDate , updateDate)
        .where(ssgPointTargetEntity.orderNo.eq(orderNo),
            ssgPointTargetEntity.buyerId.eq(buyerId),
            ssgPointTargetEntity.siteType.eq(siteType),
            ssgPointTargetEntity.tradeType.eq(tradeType),
            ssgPointTargetEntity.pointStatus.eq(PointStatusType.Fail.getCode())
        )
        .execute();
  }

  @Override
  public long updatePointStatus(final String pointStatus,
      final String manualOprt,
      final String updateOperator,
      final Instant updateDate,
      final Long orderNo,
      final String buyerId,
      final String siteType,
      final String tradeType) {
    return factory.update(ssgPointTargetEntity)
        .set(ssgPointTargetEntity.pointStatus, pointStatus)
        .set(ssgPointTargetEntity.manualOprt, manualOprt)
        .set(ssgPointTargetEntity.updateOperator, updateOperator)
        .set(ssgPointTargetEntity.updateDate, updateDate)
        .where(ssgPointTargetEntity.orderNo.eq(orderNo),
            ssgPointTargetEntity.buyerId.eq(buyerId),
            ssgPointTargetEntity.siteType.eq(siteType),
            ssgPointTargetEntity.tradeType.eq(tradeType)
        )
        .execute();
  }

  @Override
  public void updateCancelYn(final long orderNo,
      final String buyerId,
      final String siteType,
      final String tradeType,
      final String cancelYn,
      final String manualOprt,
      final String updateOperator,
      final Instant updateDate) {
    factory.update(ssgPointTargetEntity)
        .set(ssgPointTargetEntity.cancelYn , cancelYn)
        .set(ssgPointTargetEntity.manualOprt , manualOprt)
        .set(ssgPointTargetEntity.updateOperator , updateOperator)
        .set(ssgPointTargetEntity.updateDate , updateDate)
        .where(ssgPointTargetEntity.orderNo.eq(orderNo),
            ssgPointTargetEntity.buyerId.eq(buyerId),
            ssgPointTargetEntity.siteType.eq(siteType),
            ssgPointTargetEntity.tradeType.eq(tradeType)
        )
        .execute();
  }
}
