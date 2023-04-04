package com.ebaykorea.payback.batch.repository.opayreward;


import static com.ebaykorea.payback.batch.repository.opayreward.entity.QSsgPointTargetEntity.ssgPointTargetEntity;

import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
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

  public JPAQuery<SsgPointTargetEntity> findStatusByReady() {
    return factory.selectFrom(ssgPointTargetEntity)
        .where(
            ssgPointTargetEntity.tradeType.eq(PointTradeType.Save.getCode()),
            ssgPointTargetEntity.pointStatus.eq(PointStatusType.Ready.getCode()),
//            ssgPointTargetEntity.orderNo.eq(5408224778L)
            ssgPointTargetEntity.scheduleDate.between(Instant.now().minus(3, ChronoUnit.DAYS) ,Instant.now())
        );
  }

  public JPAQuery<SsgPointTargetEntity> findStatusByFail() {
    return factory.selectFrom(ssgPointTargetEntity)
        .where(
            ssgPointTargetEntity.pointStatus.eq(PointStatusType.Fail.getCode()),
            ssgPointTargetEntity.tryCount.lt(3L),
            ssgPointTargetEntity.scheduleDate.between(Instant.now().minus(1, ChronoUnit.DAYS) ,Instant.now())
        );
  }

  public List<SsgPointTargetEntity> findStatusTest() {
    return factory.selectFrom(ssgPointTargetEntity)
        .where(
            ssgPointTargetEntity.scheduleDate.between(
                    Instant.now().minus(5, ChronoUnit.DAYS),
                    Instant.now())
        )
        .fetch();
  }

  public long updatePrcoesserFailBy(final long orderNo ,
      final String siteType ,
      final String tradeType,
      final String pointStatus
  ) {
    JPAUpdateClause updateClause = factory.update(ssgPointTargetEntity);
    updateClause.set(ssgPointTargetEntity.pointStatus, PointStatusType.Fail.getCode())
            .set(ssgPointTargetEntity.tryCount, ssgPointTargetEntity.tryCount.add(1L))
        .where(ssgPointTargetEntity.pointStatus.in(PointStatusType.Ready.getCode() , PointStatusType.Fail.getCode()),
            ssgPointTargetEntity.siteType.eq(siteType),
            ssgPointTargetEntity.tradeType.eq(tradeType),
            ssgPointTargetEntity.orderNo.eq(orderNo)
        )
        .execute();
    if (PointStatusType.Fail.getCode() == pointStatus) {
      updateClause.set(ssgPointTargetEntity.tryCount, ssgPointTargetEntity.tryCount.add(1L));
    }
    return updateClause.execute();
  }

  public long updateSuceessBy(final long orderNo ,
      final String buyerId ,
      final OrderSiteType siteType ,
      final PointTradeType tradeType,
      final String accountDate,
      final Instant requestDate,
      final String responseCode,
      final String pntApprId,
      final BigDecimal saveAmount,
      final String pointStatus
  ) {
    JPAUpdateClause updateClause = factory.update(ssgPointTargetEntity);
    updateClause.set(ssgPointTargetEntity.responseCode, responseCode)
        .set(ssgPointTargetEntity.accountDate, accountDate)
        .set(ssgPointTargetEntity.requestDate, requestDate)
        .set(ssgPointTargetEntity.pntApprId, pntApprId)
        .set(ssgPointTargetEntity.updateDate, Instant.now())
        .where(ssgPointTargetEntity.pointStatus.eq(pointStatus),
            ssgPointTargetEntity.buyerId.eq(buyerId),
            ssgPointTargetEntity.siteType.eq(siteType.getShortCode()),
            ssgPointTargetEntity.tradeType.eq(tradeType.getCode()),
            ssgPointTargetEntity.orderNo.eq(orderNo)
        );
    if(responseCode.equals("PRC4081") || responseCode.equals("API0000")) {
      updateClause.set(ssgPointTargetEntity.pointStatus, PointStatusType.Success.getCode());
    } else {
      updateClause.set(ssgPointTargetEntity.pointStatus, PointStatusType.Fail.getCode());
      updateClause.set(ssgPointTargetEntity.tryCount, ssgPointTargetEntity.tryCount.add(1L));
    }

    if (saveAmount.compareTo(BigDecimal.ZERO) > 0) { // saveAmount가 0보다 클 경우에만 set 적용
      updateClause.set(ssgPointTargetEntity.saveAmount, saveAmount);
    }
    return updateClause.execute();
  }
}
