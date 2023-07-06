package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward;

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntityId;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SaturnDataSource(name = "o_payreward")
public interface SsgPointTargetRepository extends JpaRepository<SsgPointTargetEntity, SsgPointTargetEntityId>, SsgPointTargetRepositoryCustom {

    Optional<SsgPointTargetEntity> findFirstByOrderNoAndBuyerIdAndSiteTypeAndTradeType(Long orderNo,
        String buyerId,
        String siteType,
        String tradeType);

    List<SsgPointTargetEntity> findAllByOrderNoAndSiteType(Long orderNo,  String siteType);
    List<SsgPointTargetEntity> findByPackNo(Long packNo);

}
