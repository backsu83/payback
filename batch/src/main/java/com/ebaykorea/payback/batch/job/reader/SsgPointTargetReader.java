package com.ebaykorea.payback.batch.job.reader;

import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
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

  public QuerydslPagingItemReader<SsgPointTargetEntity> queryDslReader() {
    QuerydslPagingItemReader<SsgPointTargetEntity> reader = new QuerydslPagingItemReader<>(
        entityManagerFactory,
        fetchSize,
        queryFactory -> ssgPointTargetRepositorySupport.findByStatusReady()
    );
    reader.setTransacted(false); // 예시로 transacted 값을 false로 설정
    return reader;
  }
}
