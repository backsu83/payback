package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event;

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventRequestStatusEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventRequestStatusEntityId;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SaturnDataSource(name = "o_payreward")
public interface EventRequestStatusRepository extends JpaRepository<EventRequestStatusEntity, EventRequestStatusEntityId> {
}
