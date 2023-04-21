package com.ebaykorea.payback.batch.repository.opayreward;


import static com.ebaykorea.payback.batch.domain.exception.BatchProcesserExceptionCode.ERR_CARD_CRYPTO;
import static com.ebaykorea.payback.batch.domain.exception.BatchProcesserExceptionCode.ERR_PNTADD;
import static com.ebaykorea.payback.batch.domain.exception.BatchProcesserExceptionCode.ERR_PNTADDCNCL;
import static com.ebaykorea.payback.batch.domain.exception.BatchProcesserExceptionCode.ERR_TOKEN;
import static com.ebaykorea.payback.batch.repository.opayreward.entity.QSsgPointDailyVerifyEntity.ssgPointDailyVerifyEntity;
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

  public JPAQuery<SsgPointTargetEntity> findStatusByReady() {
    return factory.selectFrom(ssgPointTargetEntity)
        .where(
            ssgPointTargetEntity.pointStatus.eq(PointStatusType.Ready.getCode()),
            ssgPointTargetEntity.scheduleDate.between(Instant.now().minus(3, ChronoUnit.DAYS) ,Instant.now())
        );
  }

  public JPAQuery<SsgPointTargetEntity> findStatusByFail() {
    return factory.selectFrom(ssgPointTargetEntity)
        .where(
            ssgPointTargetEntity.pointStatus.eq(PointStatusType.Fail.getCode()),
            ssgPointTargetEntity.responseCode.in(ERR_PNTADD.name(),
                ERR_PNTADDCNCL.name(),
                ERR_CARD_CRYPTO.name(),
                ERR_TOKEN.name()),
            ssgPointTargetEntity.tryCount.lt(3),
            ssgPointTargetEntity.scheduleDate.between(Instant.now().minus(3, ChronoUnit.DAYS) ,Instant.now())
        );
  }

  public Long findStatusTest() {
    return factory.selectFrom(ssgPointTargetEntity)
        .where(
            ssgPointTargetEntity.scheduleDate.between(
                    Instant.now().minus(41, ChronoUnit.DAYS),
                    Instant.now())
        )
        .fetchCount();
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

  public long updatePointTarget(final SsgPointTargetDto target, final String pointStatus) {
    JPAUpdateClause updateClause = factory.update(ssgPointTargetEntity);
    updateClause.set(ssgPointTargetEntity.responseCode, target.getResponseCode())
        .set(ssgPointTargetEntity.accountDate, target.getAccountDate())
        .set(ssgPointTargetEntity.requestDate,  DATE_TIME_STRING_FORMATTER.parse(target.getRequestDate(), Instant::from))
        .set(ssgPointTargetEntity.pntApprId, target.getPntApprId())
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

    if(target.getResponseCode().equals("API0000")) {
      updateClause.set(ssgPointTargetEntity.pointStatus, PointStatusType.Success.getCode());
      updateClause.set(ssgPointTargetEntity.saveAmount, target.getSaveAmount());
    } else {
      updateClause.set(ssgPointTargetEntity.pointStatus, PointStatusType.Fail.getCode());
      updateClause.set(ssgPointTargetEntity.tryCount, ssgPointTargetEntity.tryCount.add(1L));
    }
    return updateClause.execute();
  }

  public SsgVerifySumEntity findSumCount(OrderSiteType orderSiteType, VerifyTradeType verifyTradeType) {
    LocalDate yesterday = LocalDate.now().minusDays(1);
    Instant startDate = LocalDateTime.of(yesterday, LocalTime.MIN).atZone(SEOUL).toInstant();
    Instant endDate = LocalDateTime.of(yesterday, LocalTime.MAX).atZone(SEOUL).toInstant();
    StringTemplate dateAsString = Expressions.stringTemplate("TO_CHAR({0}, '{1s}')", ssgPointTargetEntity.requestDate, "YYYY-MM-DD");

    var result = factory.select(
            Projections.fields(SsgVerifySumEntity.class,
                    dateAsString.as("requestDate"),
                    ssgPointTargetEntity.saveAmount.count().coalesce(0L).as("count"),
                    ssgPointTargetEntity.saveAmount.sum().coalesce(BigDecimal.ZERO).as("amount")
            )
            )
            .from(ssgPointTargetEntity)
            .where(ssgPointTargetEntity.requestDate.between(startDate, endDate),
                    ShopType(orderSiteType.getShortCode()),
                    TradeType(verifyTradeType.getShortCode()),
                    TradeType("SS")
            )
            .groupBy(dateAsString,
                    ssgPointTargetEntity.siteType,
                    ssgPointTargetEntity.tradeType,
                    ssgPointTargetEntity.pointStatus
            ).fetchOne();
    if (result == null) {
      result = new SsgVerifySumEntity();
      result.setSumCount(0L);
      result.setSumAmount(BigDecimal.ZERO);
    }
    return result;
  }

  private BooleanExpression ShopType(String shopCode) {
    return (shopCode == null || "".equals(shopCode))? null : ssgPointTargetEntity.siteType.eq(shopCode);
  }

  private BooleanExpression TradeType(String tradeType) {
    return (tradeType == null || "".equals(tradeType))? null : ssgPointTargetEntity.tradeType.eq(tradeType);
  }


}
