package com.ebaykorea.payback.batch.repository.opayreward;

import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntityId;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SaturnDataSource(name = "o_payreward")
public interface SsgPointTargetRepository extends JpaRepository<SsgPointTargetEntity, SsgPointTargetEntityId> {

}
