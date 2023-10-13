package com.ebaykorea.payback.scheduler.repository.opayreward;

import com.ebaykorea.payback.scheduler.repository.opayreward.entity.event.EventRewardRequestEntity;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.ebaykorea.payback.scheduler.domain.constant.EventRequestStatusType.Requested;
import static com.ebaykorea.payback.scheduler.repository.opayreward.entity.event.QEventRewardRequestEntity.eventRewardRequestEntity;
import static com.ebaykorea.payback.scheduler.repository.opayreward.entity.event.QEventRewardRequestStatusEntity.eventRewardRequestStatusEntity;
import static com.querydsl.core.types.ExpressionUtils.isNull;

@Repository
@RequiredArgsConstructor
@SaturnDataSource(name = "o_payreward")
public class EventRewardRepositoryCustom {
  private final JPAQueryFactory factory;

  public JPAQuery<EventRewardRequestEntity> findNotRequestedRequests() {
    return factory.selectFrom(eventRewardRequestEntity)
        .leftJoin(eventRewardRequestEntity.statuses, eventRewardRequestStatusEntity)
        .on(eventRewardRequestStatusEntity.eventRequestStatus.eq(Requested))
        .where(isNull(eventRewardRequestStatusEntity.requestNo));
  }
}
