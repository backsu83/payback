package com.ebaykorea.payback.core.persistence.mssql.stardb.reward.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CashbackOrderEntityId.class)
@Table(name = "CASHBACK_ORDER")
public class CashbackOrderEntity implements Serializable {

    @Id
    @Column(name = "BUY_ORDER_NO")
    private long buyOrderNo;

    @Id
    @Column(name = "CASHBACK_TYPE")
    private String cashbackType;

    @Id
    @Column(name = "TRADE_CD")
    private String tradeCd;

    @Column(name = "CASHBACK_MONEY")
    private BigDecimal cashbackMoney;

    @Column(name = "CASHBACK_BASIS_MONEY")
    private BigDecimal cashbackBasisMoney;

    @Column(name = "GD_NO")
    private String gdNo;

    @Column(name = "PACK_NO")
    private long packNo;

    @Column(name = "CUST_NO")
    private String custNo;

    @Column(name = "USER_KEY")
    private String userKey;

    @Column(name = "TRADE_STATUS")
    private String tradeStatus;

    @Column(name = "USE_ENABLE_DT")
    private Instant useEnableDt;

    @Column(name = "SITE_TYPE")
    private String siteType;

    @Column(name = "CASHBACK_REQ_SEQ")
    private Long cashbackReqSeq;

    @Column(name = "SMILE_CLUB_YN")
    private Boolean smileClubYn;

    @Column(name = "SHOP_TYPE")
    private String shopType;

    @Column(name = "REQ_CANCEL_YN")
    private Boolean isRequestCancel;

    @Column(name = "REG_ID")
    private String regId;

    @Column(name = "CHG_ID")
    private String chgId;


//    @Column(name = "CLUB_DAY_MAX_SAVE_RATE")
//    private BigDecimal clubDayMaxSaveRate;
}
