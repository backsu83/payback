package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward;

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointOrderNoEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointOrderNoEntityId;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SaturnDataSource(name = "o_payreward")
public interface SsgPointOrderNoRepository extends JpaRepository<SsgPointOrderNoEntity, SsgPointOrderNoEntityId>  {

}
