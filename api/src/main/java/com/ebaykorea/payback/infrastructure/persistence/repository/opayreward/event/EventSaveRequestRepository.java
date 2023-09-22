package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event;

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventSaveRequestEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventSaveRequestEntityId;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SaturnDataSource(name = "o_payreward")
public interface EventSaveRequestRepository extends JpaRepository<EventSaveRequestEntity, EventSaveRequestEntityId> {

}
