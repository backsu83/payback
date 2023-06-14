package com.ebaykorea.payback.batch.job.reader;


import com.ebaykorea.payback.batch.job.reader.expression.Expression;
import com.ebaykorea.payback.batch.job.reader.options.QuerydslNoOffsetNumberOptions;
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.ebaykorea.payback.batch.repository.opayreward.entity.QSsgPointTargetEntity.ssgPointTargetEntity;

@Component
@RequiredArgsConstructor
public class SsgPointTargetRecoverReader {

  private final EntityManagerFactory entityManagerFactory;
  private final SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;

  @Value("${ssgpoint.batch.fetchSize}")
  private int fetchSize;

  public QuerydslPagingItemReader<SsgPointTargetEntity> queryDslReader(){
    QuerydslNoOffsetNumberOptions<SsgPointTargetEntity, Long> options = new QuerydslNoOffsetNumberOptions<>(ssgPointTargetEntity.orderNo, Expression.DESC);
    QuerydslNoOffsetPagingItemReader<SsgPointTargetEntity> reader = new QuerydslNoOffsetPagingItemReader<>(
            entityManagerFactory,
            fetchSize,
            options,
            queryFactory -> ssgPointTargetRepositorySupport.findResponseCodeForRetry());
    return reader;
  }

}
