package com.ebaykorea.payback.batch.job.reader;

import static com.ebaykorea.payback.batch.repository.opayreward.entity.QSsgPointTargetEntity.ssgPointTargetEntity;

import com.ebaykorea.payback.batch.job.reader.expression.Expression;
import com.ebaykorea.payback.batch.job.reader.options.QuerydslNoOffsetNumberOptions;
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;

import com.querydsl.jpa.impl.JPAQuery;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SsgPointTargetReader  {

  private final EntityManagerFactory entityManagerFactory;
  private final SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;

  @Value("${ssgpoint.batch.fetchSize}")
  private int fetchSize;

  public QuerydslNoOffsetPagingItemReader<SsgPointTargetEntity> earnReader(){
    QuerydslNoOffsetNumberOptions<SsgPointTargetEntity, Long> options = new QuerydslNoOffsetNumberOptions<>(ssgPointTargetEntity.orderNo, Expression.DESC);
    QuerydslNoOffsetPagingItemReader<SsgPointTargetEntity> reader = new QuerydslNoOffsetPagingItemReader<>(
            entityManagerFactory,
            fetchSize,
            options,
            queryFactory -> ssgPointTargetRepositorySupport.findStatusForEarn());
    return reader;
  }

  public QuerydslPagingItemReader<SsgPointTargetEntity> cancelReader(){
    QuerydslNoOffsetNumberOptions<SsgPointTargetEntity, Long> options = new QuerydslNoOffsetNumberOptions<>(ssgPointTargetEntity.orderNo, Expression.DESC);
    QuerydslNoOffsetPagingItemReader<SsgPointTargetEntity> reader = new QuerydslNoOffsetPagingItemReader<>(
            entityManagerFactory,
            fetchSize,
            options,
            queryFactory -> ssgPointTargetRepositorySupport.findStatusForCancel());
    return reader;
  }
}
