package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward;


import static com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.QSsgPointTargetEntity.ssgPointTargetEntity;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.util.PaybackStrings;
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


  public long insertSsgPoint(SsgPointTargetEntity entity) {
    return factory.insert(ssgPointTargetEntity)
        .set(ssgPointTargetEntity.orderNo , entity.getOrderNo())
        .set(ssgPointTargetEntity.buyerId , entity.getBuyerId())
        .set(ssgPointTargetEntity.siteType , entity.getSiteType())
        .set(ssgPointTargetEntity.tradeType , PointTradeType.Save.getCode())
        .set(ssgPointTargetEntity.receiptNo , entity.getReceiptNo())
        .set(ssgPointTargetEntity.orgReceiptNo , PaybackStrings.EMPTY)
        .set(ssgPointTargetEntity.pntApprId , PaybackStrings.EMPTY)
        .set(ssgPointTargetEntity.orgPntApprId , PaybackStrings.EMPTY)
        .set(ssgPointTargetEntity.payAmount , entity.getPayAmount())
        .set(ssgPointTargetEntity.saveAmount , entity.getSaveAmount())
        .set(ssgPointTargetEntity.pointStatus , PointStatusType.Ready.getCode())
        .set(ssgPointTargetEntity.cancelYn , entity.getCancelYn())
        .set(ssgPointTargetEntity.pointToken , entity.getPointToken())
        .set(ssgPointTargetEntity.orderDate , entity.getOrderDate())
        .set(ssgPointTargetEntity.scheduleDate , entity.getScheduleDate())
        .set(ssgPointTargetEntity.requestDate , entity.getRequestDate())
        .set(ssgPointTargetEntity.accountDate , entity.getPointToken())
        .set(ssgPointTargetEntity.responseCode , entity.getPointToken())
        .set(ssgPointTargetEntity.trcNo , entity.getPointToken())
        .set(ssgPointTargetEntity.tradeNo , entity.getPointToken())
        .set(ssgPointTargetEntity.packNo , entity.getPackNo())
        .set(ssgPointTargetEntity.manualOprt , entity.getPointToken())
        .set(ssgPointTargetEntity.adminCancelYn , entity.getPointToken())
        .set(ssgPointTargetEntity.tryCount , entity.getPointToken())
        .set(ssgPointTargetEntity.updateDate , entity.getUpdateDate())
        .set(ssgPointTargetEntity.updateOperator , entity.getUpdateOperator())
        .set(ssgPointTargetEntity.insertDate , entity.getInsertDate())
        .set(ssgPointTargetEntity.insertOperator , entity.getInsertOperator())
        .execute();

  }
}
