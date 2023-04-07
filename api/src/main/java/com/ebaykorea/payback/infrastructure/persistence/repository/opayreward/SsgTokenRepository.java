package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward;

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgTokenEntity;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import java.time.Instant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SaturnDataSource(name = "o_payreward")
public interface SsgTokenRepository extends JpaRepository<SsgTokenEntity, String> {

  SsgTokenEntity findTopByExpireDateAfterOrderByExpireDateDesc(Instant expireDate);

}
