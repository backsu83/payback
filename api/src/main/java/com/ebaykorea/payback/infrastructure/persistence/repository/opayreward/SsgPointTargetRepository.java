package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward;

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntityId;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@SaturnDataSource(name = "o_payreward")
public interface SsgPointTargetRepository extends JpaRepository<SsgPointTargetEntity, SsgPointTargetEntityId>  {
    List<SsgPointTargetEntity> findByPackNo(Long packNo);
}
