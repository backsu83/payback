package com.ebaykorea.payback.scheduler.repository.opayreward;

import com.ebaykorea.payback.scheduler.repository.opayreward.entity.CancelConsumerFailEntity;
import com.ebaykorea.payback.scheduler.repository.opayreward.entity.CancelConsumerFailEntityId;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SaturnDataSource(name = "o_payreward")
public interface CancelConsumerFailRepository extends JpaRepository<CancelConsumerFailEntity, CancelConsumerFailEntityId> {
    List<CancelConsumerFailEntity> findTop100ByStatusAndTryCount(String status, Long tryCount);
}
