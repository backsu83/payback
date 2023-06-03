package com.ebaykorea.payback.batch.repository.opayreward;


import static com.ebaykorea.payback.batch.domain.exception.BatchProcesserExceptionCode.*;
import static com.ebaykorea.payback.batch.repository.opayreward.entity.QSsgPointTargetEntity.ssgPointTargetEntity;
import static com.ebaykorea.payback.batch.util.PaybackDateTimes.DATE_TIME_STRING_FORMATTER;

import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import com.ebaykorea.payback.batch.domain.constant.VerifyTradeType;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgVerifySumEntity;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
@SaturnDataSource(name = "o_payreward")
public class SsgPointTargetRepositorySupport extends QuerydslRepositorySupport {
  private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");
  private final JPAQueryFactory factory;
  public SsgPointTargetRepositorySupport(JPAQueryFactory factory) {
    super(SsgPointTargetEntity.class);
    this.factory = factory;
  }

  public JPAQuery<SsgPointTargetEntity> findStatusForEarn() {
    return factory.selectFrom(ssgPointTargetEntity)
        .where(
            ssgPointTargetEntity.pointStatus.eq(PointStatusType.Ready.getCode()),
            ssgPointTargetEntity.tradeType.eq(PointTradeType.Save.getCode()),
            ssgPointTargetEntity.scheduleDate.between(Instant.now().minus(3, ChronoUnit.DAYS) ,Instant.now())
        ).orderBy(ssgPointTargetEntity.scheduleDate.desc());
  }

  public JPAQuery<SsgPointTargetEntity> findStatusForCancel() {
    return factory.selectFrom(ssgPointTargetEntity)
        .where(
            ssgPointTargetEntity.pointStatus.eq(PointStatusType.Ready.getCode()),
            ssgPointTargetEntity.tradeType.eq(PointTradeType.Cancel.getCode()),
            ssgPointTargetEntity.scheduleDate.between(Instant.now().minus(3, ChronoUnit.DAYS) ,Instant.now())
        ).orderBy(ssgPointTargetEntity.scheduleDate.desc());
  }

  public JPAQuery<SsgPointTargetEntity> findStatusByFail() {
    return factory.selectFrom(ssgPointTargetEntity)
        .where(
            ssgPointTargetEntity.pointStatus.eq(PointStatusType.Fail.getCode()),
            ssgPointTargetEntity.responseCode.in(ERR_PNTADD.name(),
                ERR_PNTADDCNCL.name(),
                ERR_CARD_CRYPTO.name(),
                ERR_TOKEN.name(),
                ERR_ADMIN.name()),
            ssgPointTargetEntity.tryCount.lt(3),
            ssgPointTargetEntity.scheduleDate.between(Instant.now().minus(7, ChronoUnit.DAYS) ,Instant.now())
        );
  }

  public long updatePrcoesserFailBy(final long orderNo ,
      final String siteType ,
      final String tradeType,
      final String errorCode
  ) {
    return factory.update(ssgPointTargetEntity)
        .set(ssgPointTargetEntity.pointStatus, PointStatusType.Fail.getCode())
        .set(ssgPointTargetEntity.tryCount, ssgPointTargetEntity.tryCount.add(1L))
        .set(ssgPointTargetEntity.responseCode, errorCode)
        .where(ssgPointTargetEntity.pointStatus.in(PointStatusType.Ready.getCode() , PointStatusType.Fail.getCode()),
            ssgPointTargetEntity.siteType.eq(siteType),
            ssgPointTargetEntity.tradeType.eq(tradeType),
            ssgPointTargetEntity.orderNo.eq(orderNo)
        )
        .execute();
  }
  public long updatePointTarget(final SsgPointTargetDto target,
      final BigDecimal saveAmount ,
      final String pntApprId,
      final boolean isSuccess,
      final String pointStatus) {
    JPAUpdateClause updateClause = factory.update(ssgPointTargetEntity);
    updateClause.set(ssgPointTargetEntity.responseCode, target.getResponseCode())
        .set(ssgPointTargetEntity.accountDate, target.getAccountDate())
        .set(ssgPointTargetEntity.requestDate,  DATE_TIME_STRING_FORMATTER.parse(target.getRequestDate(), Instant::from))
        .set(ssgPointTargetEntity.pntApprId, pntApprId)
        .set(ssgPointTargetEntity.updateDate, Instant.now())
        .where(ssgPointTargetEntity.pointStatus.eq(pointStatus),
            ssgPointTargetEntity.buyerId.eq(target.getBuyerId()),
            ssgPointTargetEntity.siteType.eq(target.getSiteType().getShortCode()),
            ssgPointTargetEntity.tradeType.eq(target.getTradeType().getCode()),
            ssgPointTargetEntity.orderNo.eq(target.getOrderNo())
        );

    if(target.getTradeType() == PointTradeType.Save) {
      updateClause.set(ssgPointTargetEntity.pointToken, target.getPointToken());
    }

    if(isSuccess) {
      updateClause.set(ssgPointTargetEntity.pointStatus, PointStatusType.Success.getCode());
      updateClause.set(ssgPointTargetEntity.saveAmount, saveAmount);
    } else {
      updateClause.set(ssgPointTargetEntity.pointStatus, PointStatusType.Fail.getCode());
      updateClause.set(ssgPointTargetEntity.tryCount, ssgPointTargetEntity.tryCount.add(1L));
    }
    return updateClause.execute();
  }

  public long updatePntApprIdForCancelTradeType(final SsgPointTargetDto target)
  {
    return factory.update(ssgPointTargetEntity)
        .set(ssgPointTargetEntity.orgPntApprId, target.getPntApprId())
        .set(ssgPointTargetEntity.pointToken, target.getPointToken())
        .set(ssgPointTargetEntity.accountDate, target.getAccountDate())
        .where(ssgPointTargetEntity.pointStatus.eq(PointStatusType.Ready.getCode()),
            ssgPointTargetEntity.tradeType.eq(PointTradeType.Cancel.getCode()),
            ssgPointTargetEntity.orderNo.eq(target.getOrderNo())
        )
        .execute();
  }

  public SsgVerifySumEntity findSumCount(OrderSiteType orderSiteType, VerifyTradeType verifyTradeType) {

    LocalDate yesterday = LocalDate.now().minusDays(1);
    Instant startDate = LocalDateTime.of(yesterday, LocalTime.MIN).atZone(SEOUL).toInstant();
    Instant endDate = LocalDateTime.of(yesterday, LocalTime.MAX).atZone(SEOUL).toInstant();
    StringTemplate dateAsString = Expressions.stringTemplate("TO_CHAR({0}, '{1s}')", ssgPointTargetEntity.requestDate, "YYYY-MM-DD");

    var result = factory.select(
              Projections.fields(SsgVerifySumEntity.class,
                      ssgPointTargetEntity.saveAmount.count().as("sumCount"),
                      ssgPointTargetEntity.saveAmount.sum().as("sumAmount")
              )
            )
            .from(ssgPointTargetEntity)
            .where(ssgPointTargetEntity.requestDate.between(startDate, endDate),
                    ShopType(orderSiteType.getShortCode()),
                    TradeType(verifyTradeType.getShortCode()),
                    PointStatus(PointStatusType.Success.getCode())
            )
            .groupBy(ssgPointTargetEntity.siteType,
                    ssgPointTargetEntity.tradeType,
                    ssgPointTargetEntity.pointStatus
            ).fetchOne();
    return Optional.ofNullable(result)
            .orElse(new SsgVerifySumEntity(0L, BigDecimal.ZERO));

  }

  private BooleanExpression ShopType(String shopCode) {
    return (shopCode == null || "".equals(shopCode))? null : ssgPointTargetEntity.siteType.eq(shopCode);
  }

  private BooleanExpression TradeType(String tradeType) {
    return (tradeType == null || "".equals(tradeType))? null : ssgPointTargetEntity.tradeType.eq(tradeType);
  }

  private BooleanExpression PointStatus(String pointStatus) {
    return (pointStatus == null || "".equals(pointStatus))? null : ssgPointTargetEntity.pointStatus.eq(pointStatus);
  }


}
