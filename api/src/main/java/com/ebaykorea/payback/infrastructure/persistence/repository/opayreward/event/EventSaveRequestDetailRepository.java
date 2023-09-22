package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event;

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventSaveRequestDetailEntity;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@SaturnDataSource(name = "o_payreward")
public interface EventSaveRequestDetailRepository extends JpaRepository<EventSaveRequestDetailEntity, Long> {
  @Query(value = "SELECT O_PAYREWARD.SEQ_EVENT_SAVE_REQUEST_DETAIL.nextval FROM dual", nativeQuery = true)
  Long getNextSeq();
}
