package com.ebaykorea.payback.batch.repository.opayreward;

import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointDailyVerifyEntity;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SaturnDataSource(name = "o_payreward")
public interface SsgPointDailyVerifyRepository extends JpaRepository<SsgPointDailyVerifyEntity, Long> {
     @Override
     <S extends SsgPointDailyVerifyEntity> S save(S entity);
}
