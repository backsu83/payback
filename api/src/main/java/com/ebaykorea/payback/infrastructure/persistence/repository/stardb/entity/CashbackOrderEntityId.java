package com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CashbackOrderEntityId implements Serializable {

    @Column(name = "BUY_ORDER_NO")
    private long buyOrderNo;

    @Column(name = "CASHBACK_TYPE")
    private String cashbackType;

    @Column(name = "TRADE_CD")
    private String tradeCd;
}
