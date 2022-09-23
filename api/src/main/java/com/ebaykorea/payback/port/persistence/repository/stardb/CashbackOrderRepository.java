package com.ebaykorea.payback.port.persistence.repository.stardb;

import com.ebaykorea.payback.port.persistence.repository.stardb.entity.CashbackOrderEntity;
import com.ebaykorea.payback.port.persistence.repository.stardb.entity.CashbackOrderEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CashbackOrderRepository extends JpaRepository<CashbackOrderEntity, CashbackOrderEntityId> {

    /**
     * 쿼리문 데이터 추출
     * nativeQuery = true 경우 ms-sql 사용
     * nativeQuery = false 경우 jpql 사용
     * @param buyOrderNo
     */
    @Query(
            nativeQuery = true,
            value = "select * from dbo.CASHBACK_ORDER  with(nolock) where BUY_ORDER_NO = :buyOrderNo"
    )
    CashbackOrderEntity findByBuyOrderNo(@Param(value = "buyOrderNo") long buyOrderNo );
}
