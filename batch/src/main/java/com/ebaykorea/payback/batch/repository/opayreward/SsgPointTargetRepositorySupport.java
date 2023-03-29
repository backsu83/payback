package com.ebaykorea.payback.batch.repository.opayreward;


import static com.ebaykorea.payback.batch.repository.opayreward.entity.QSsgPointTargetEntity.ssgPointTargetEntity;
import static com.ebaykorea.payback.batch.util.PaybackInstants.endOfDay;
import static com.ebaykorea.payback.batch.util.PaybackInstants.startOfDay;

import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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

  public JPAQuery<SsgPointTargetEntity> findByStatusReady() {
    return factory.select(ssgPointTargetEntity)
        .from(ssgPointTargetEntity)
        .where(ssgPointTargetEntity.pointStatus.eq(PointStatusType.Ready.getCode()),
            ssgPointTargetEntity.orderNo.eq(5408227299L)
        );
  }

  public List<SsgPointTargetEntity> findByStatusReadyBy() {
    return factory.select(ssgPointTargetEntity)
        .from(ssgPointTargetEntity)
        .where(ssgPointTargetEntity.pointStatus.eq(PointStatusType.Ready.getCode()),
            ssgPointTargetEntity.scheduleDate.between(startOfDay() , endOfDay())
        )
        .fetch();
  }

  public long updateFailBy(final long orderNo , final String siteType , final String tradeType) {
    return factory.update(ssgPointTargetEntity)
        .set(ssgPointTargetEntity.pointStatus, PointStatusType.Fail.getCode())
        .where(ssgPointTargetEntity.pointStatus.eq(PointStatusType.Ready.getCode()),
            ssgPointTargetEntity.siteType.eq(siteType),
            ssgPointTargetEntity.tradeType.eq(tradeType),
            ssgPointTargetEntity.orderNo.eq(orderNo)
        )
        .execute();
  }
}
