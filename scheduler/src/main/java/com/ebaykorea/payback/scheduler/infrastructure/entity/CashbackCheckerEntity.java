package com.ebaykorea.payback.scheduler.infrastructure.entity;

import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashbackCheckerEntity {
    //임시 SP ( 캐시백이 적립되지 않는 주문키를 가져온다 )
    public static final String FIND_BY_KEYS = "stardb.dbo.UPGMKT_Payback_NoCashbackChecker_Select";

    @Column(name = "ORDER_KEY")
    private String orderKey;

    @Column(name = "TX_KEY")
    private String txKey;


}
