package com.ebaykorea.payback.batch.repository.opayreward;


import static com.ebaykorea.payback.batch.repository.opayreward.entity.QSsgPointTargetEntity.ssgPointTargetEntity;

import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
@SaturnDataSource(name = "o_payreward")
public class SsgPointTargetRepositorySupport extends QuerydslRepositorySupport {

  private final JPAQueryFactory factory;

  public SsgPointTargetRepositorySupport(JPAQueryFactory factory) {
    super(SsgPointTargetEntity.class);
    this.factory = factory;
  }

  public SsgPointTargetEntity findByPointStatus(long orderNo, OrderSiteType orderSiteType) {
    return factory.select(ssgPointTargetEntity)
        .from(ssgPointTargetEntity)
        .where(ssgPointTargetEntity.orderNo.eq(orderNo)
            .and(ssgPointTargetEntity.tradeType.eq(PointTradeType.Save.getCode()))
            .and(ssgPointTargetEntity.siteType.eq(orderSiteType.getShortCode()))
            .and(ssgPointTargetEntity.pointStatus.eq(PointStatusType.Success.getCode()))
        )
        .fetchOne();
  }
}
