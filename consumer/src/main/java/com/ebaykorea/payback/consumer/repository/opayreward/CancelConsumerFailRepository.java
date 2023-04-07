package com.ebaykorea.payback.consumer.repository.opayreward;

import com.ebaykorea.payback.consumer.repository.opayreward.entity.CancelConsumerFailEntity;
import com.ebaykorea.payback.consumer.repository.opayreward.entity.CancelConsumerFailEntityId;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SaturnDataSource(name = "o_payreward")
public interface CancelConsumerFailRepository extends JpaRepository<CancelConsumerFailEntity, CancelConsumerFailEntityId> {

}
