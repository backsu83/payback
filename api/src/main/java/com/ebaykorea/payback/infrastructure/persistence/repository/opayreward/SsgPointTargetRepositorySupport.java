package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward;


import static com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.QSsgPointTargetEntity.ssgPointTargetEntity;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity;
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

  public SsgPointTargetEntity findByPointStatusReady(long orderNo, String buyerId ,OrderSiteType orderSiteType) {
    return factory.select(ssgPointTargetEntity)
        .from(ssgPointTargetEntity)
        .where(ssgPointTargetEntity.orderNo.eq(orderNo)
            .and(ssgPointTargetEntity.tradeType.eq(PointTradeType.Save.getCode()))
            .and(ssgPointTargetEntity.siteType.eq(orderSiteType.getShortCode()))
            .and(ssgPointTargetEntity.buyerId.eq(buyerId))
                //.and(ssgPointTargetEntity.pointStatus.eq(PointStatusType.Success.getCode()))
        )
        .fetchOne();
  }
}
