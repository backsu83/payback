package com.ebaykorea.payback.batch.repository.opayreward;


import static com.ebaykorea.payback.batch.repository.opayreward.entity.QSsgPointTargetEntity.ssgPointTargetEntity;

import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
    return factory.selectFrom(ssgPointTargetEntity)
        .where(
            ssgPointTargetEntity.pointStatus.eq(PointStatusType.Ready.getCode()),
            ssgPointTargetEntity.orderNo.eq(5408227299L)
        );
  }


  public List<SsgPointTargetEntity> findByStatusReadyTest() {
    return factory.selectFrom(ssgPointTargetEntity)
        .where(
            ssgPointTargetEntity.pointStatus.eq(PointStatusType.Ready.getCode()),
            ssgPointTargetEntity.scheduleDate.between(Instant.now().minus(14, ChronoUnit.DAYS) ,Instant.now())
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

  public long updateSuceessBy(final long orderNo ,
      final String buyerId ,
      final OrderSiteType siteType ,
      final PointTradeType tradeType,
      final String accountDate,
      final Instant requestDate,
      final String responseCode,
      final String pntApprId,
      final BigDecimal saveAmount
  ) {
    return factory.update(ssgPointTargetEntity)
        .set(ssgPointTargetEntity.pointStatus, PointStatusType.Success.getCode())
        .set(ssgPointTargetEntity.responseCode, responseCode)
        .set(ssgPointTargetEntity.accountDate, accountDate)
        .set(ssgPointTargetEntity.requestDate, requestDate)
        .set(ssgPointTargetEntity.saveAmount, saveAmount)
        .set(ssgPointTargetEntity.pntApprId, pntApprId)
        .set(ssgPointTargetEntity.updateDate, Instant.now())
        .where(ssgPointTargetEntity.pointStatus.eq(PointStatusType.Ready.getCode()),
            ssgPointTargetEntity.buyerId.eq(buyerId),
            ssgPointTargetEntity.siteType.eq(siteType.getShortCode()),
            ssgPointTargetEntity.tradeType.eq(tradeType.getCode()),
            ssgPointTargetEntity.orderNo.eq(orderNo)
        )
        .execute();
  }
}
