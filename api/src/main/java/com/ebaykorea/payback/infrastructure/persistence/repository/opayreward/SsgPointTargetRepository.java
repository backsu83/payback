package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;
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
    @Query("update SsgPointTargetEntity s set s.pointStatus = ?1, s.tryCount = ?2, s.manualOprt = ?3, s.updateDate = ?4 " +
            "where s.orderNo = ?5 and s.buyerId = ?6 and s.siteType = ?7 and s.tradeType = ?8 and s.pointStatus = ?9")
    int retryFailPointStatus(@NonNull String pointStatus, @NonNull Long tryCount, @NonNull String manualOprt, @NonNull Instant updateDate, @NonNull Long orderNo, @NonNull String buyerId, @NonNull String siteType, @NonNull String tradeType, @NonNull String pointStatus1);

    Optional<SsgPointTargetEntity> findFirstByOrderNoAndBuyerIdAndSiteTypeAndTradeType(@NonNull Long orderNo, @NonNull String buyerId, @NonNull String siteType, @NonNull String tradeType);

    @Transactional
    @Modifying
    @Query("update SsgPointTargetEntity s set s.pointStatus = ?1, s.manualOprt = ?2, s.updateOperator = ?3, s.updateDate = ?4 " +
            "where s.orderNo = ?5 and s.buyerId = ?6 and s.siteType = ?7 and s.tradeType = ?8")
    int updatePointStatus(String pointStatus, @Nullable String manualOprt, String updateOperator, Instant updateDate, @NonNull Long orderNo, @NonNull String buyerId, @NonNull String siteType, @NonNull String tradeType);

    @Transactional
    @Modifying
    @Query("update SsgPointTargetEntity s set s.cancelYn = ?5, s.manualOprt = ?6, s.updateOperator = ?7, s.updateDate = ?8 " +
        "where s.orderNo = ?1 and s.buyerId = ?2 and s.siteType = ?3 and s.tradeType = ?4")
    void updateCancelYn(long orderNo, String buyerId, String siteType, String tradeType,
                        String cancelYn, String manualOprt, String updateOperator, Instant updateDate);

    List<SsgPointTargetEntity> findAllByPackNoAndBuyerIdAndSiteTypeAndTradeType(Long packNo, String buyerId, String siteType, String tradeType);

    List<SsgPointTargetEntity> findAllByOrderNoAndSiteType(Long orderNo,  String siteType);
    List<SsgPointTargetEntity> findByPackNo(Long packNo);

    List<SsgPointTargetEntity> findByBuyerIdAndPointStatus(String buyerId, String pointStatus);
}
