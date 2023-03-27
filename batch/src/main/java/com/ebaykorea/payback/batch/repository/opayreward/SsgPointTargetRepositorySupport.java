package com.ebaykorea.payback.batch.repository.opayreward;


import static com.ebaykorea.payback.batch.repository.opayreward.entity.QSsgPointTargetEntity.ssgPointTargetEntity;
import static com.ebaykorea.payback.batch.util.PaybackInstants.endOfDay;
import static com.ebaykorea.payback.batch.util.PaybackInstants.startOfDay;

import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

  public JPAQuery<SsgPointTargetEntity> findByStatusReady() {
    return factory.select(ssgPointTargetEntity)
        .from(ssgPointTargetEntity)
        .where(ssgPointTargetEntity.pointStatus.eq(PointStatusType.Ready.getCode()),
            ssgPointTargetEntity.orderNo.eq(5408227299L)
        );
  }

  public List<SsgPointTargetEntity> findByStatusReady2() {
    return factory.select(ssgPointTargetEntity)
        .from(ssgPointTargetEntity)
        .where(ssgPointTargetEntity.pointStatus.eq(PointStatusType.Ready.getCode()))
        .limit(1) //임시
        .fetch();
  }
}
