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

  public QuerydslPagingItemReader<SsgPointTargetEntity> queryDslReader(){
    QuerydslPagingItemReader<SsgPointTargetEntity> reader = new QuerydslPagingItemReader<>(
            entityManagerFactory,
            fetchSize,
            queryFactory -> ssgPointTargetRepositorySupport.findStatusByReady()
    );
    return reader;
  }
}
