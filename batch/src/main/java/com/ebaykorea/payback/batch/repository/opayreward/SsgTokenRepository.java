package com.ebaykorea.payback.batch.repository.opayreward;

import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgTokenEntity;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import java.time.Instant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SaturnDataSource(name = "o_payreward")
public interface SsgTokenRepository extends JpaRepository<SsgTokenEntity, String> {
  SsgTokenEntity findTopBySiteTypeAndExpireDateAfterOrderByExpireDateDesc(String siteType, Instant expireDate);


}
