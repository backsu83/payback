package com.ebaykorea.payback.scheduler.repository.opayreward;

import com.ebaykorea.payback.scheduler.repository.opayreward.entity.event.EventRewardRequestEntity;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static com.ebaykorea.payback.scheduler.domain.constant.EventRequestStatusType.Requested;
import static com.ebaykorea.payback.scheduler.repository.opayreward.entity.event.QEventRewardRequestEntity.eventRewardRequestEntity;
import static com.ebaykorea.payback.scheduler.repository.opayreward.entity.event.QEventRewardRequestStatusEntity.eventRewardRequestStatusEntity;
import static com.querydsl.core.types.ExpressionUtils.isNull;

@Repository
@RequiredArgsConstructor
@SaturnDataSource(name = "o_payreward")
public class EventRewardRepositoryCustom {
  private final JPAQueryFactory factory;

  public List<EventRewardRequestEntity> findNotRequestedRequests(final String tenantId, final String startDate, final String endDate) {
    return factory.selectFrom(eventRewardRequestEntity)
        .leftJoin(eventRewardRequestEntity.statuses, eventRewardRequestStatusEntity)
        .on(eventRewardRequestStatusEntity.eventRequestStatus.eq(Requested))
        .where(isNull(eventRewardRequestStatusEntity.requestNo),
            equalTenantId(tenantId),
            eventRewardRequestEntity.insertDate.goe(formattedDate(startDate)),
            eventRewardRequestEntity.insertDate.lt(formattedDate(endDate)))
        .fetch();
  }

  private BooleanExpression equalTenantId(final String tenantId) {
    return StringUtils.hasLength(tenantId) ? eventRewardRequestEntity.tenantId.eq(tenantId) : null;
  }

  private StringExpression formattedDate() {
    return Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", eventRewardRequestEntity.insertDate);
  }

  private DateTemplate<Instant> formattedDate(final String dateString) {
    return Expressions.dateTemplate(Instant.class, "to_date({0}, {1})", dateString, Expressions.constant("yyyyMMdd"));
  }
}
