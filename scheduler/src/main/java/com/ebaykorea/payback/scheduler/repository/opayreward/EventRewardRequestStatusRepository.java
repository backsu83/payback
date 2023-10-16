package com.ebaykorea.payback.scheduler.repository.opayreward;

import com.ebaykorea.payback.scheduler.repository.opayreward.entity.event.EventRewardRequestStatusEntity;
import com.ebaykorea.payback.scheduler.repository.opayreward.entity.event.EventRewardRequestStatusEntityId;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SaturnDataSource(name = "o_payreward")
public interface EventRewardRequestStatusRepository extends JpaRepository<EventRewardRequestStatusEntity, EventRewardRequestStatusEntityId> {
}