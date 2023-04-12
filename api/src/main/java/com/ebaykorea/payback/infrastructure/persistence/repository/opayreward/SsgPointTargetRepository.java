package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward;

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntityId;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
@SaturnDataSource(name = "o_payreward")
public interface SsgPointTargetRepository extends JpaRepository<SsgPointTargetEntity, SsgPointTargetEntityId>  {
    @Transactional
    @Modifying
    @Query("update SsgPointTargetEntity s set s.pointStatus = ?1, s.tryCount = ?2, s.manualOprt = ?3, s.updateOperator = ?4, s.updateDate = ?5 " +
            "where s.orderNo = ?6 and s.buyerId = ?7 and s.siteType = ?8 and s.tradeType = ?9 and s.pointStatus = ?10")
    int retryFailPointStatus(@NonNull String pointStatus, @NonNull Long tryCount, @NonNull String manualOprt, @NonNull String updateOperator, @NonNull Instant updateDate, @NonNull Long orderNo, @NonNull String buyerId, @NonNull String siteType, @NonNull String tradeType, @NonNull String pointStatus1);

    Optional<SsgPointTargetEntity> findFirstByOrderNoAndBuyerIdAndSiteTypeAndTradeType(@NonNull Long orderNo, @NonNull String buyerId, @NonNull String siteType, @NonNull String tradeType);

    @Transactional
    @Modifying
    @Query("update SsgPointTargetEntity s set s.pointStatus = ?1, s.manualOprt = ?2, s.updateOperator = ?3, s.updateDate = ?4 " +
            "where s.orderNo = ?5 and s.buyerId = ?6 and s.siteType = ?7 and s.tradeType = ?8")
    int updateCancelStatus(String pointStatus, @Nullable String manualOprt, String updateOperator, Instant updateDate, @NonNull Long orderNo, @NonNull String buyerId, @NonNull String siteType, @NonNull String tradeType);

    List<SsgPointTargetEntity> findAllByPackNoAndSiteType(Long packNo, String siteType);

    List<SsgPointTargetEntity> findAllByOrderNoAndSiteType(Long orderNo, String buyerId, String siteType);
}
