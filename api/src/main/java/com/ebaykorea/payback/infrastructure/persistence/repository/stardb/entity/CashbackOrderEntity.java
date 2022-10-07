package com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashbackOrderEntity {

    //FIXME: sp 새로 만들기 (Carbon -> Payback)
    public static final String FIND_BY_ID = "stardb.dbo.UPGMKT_Carbon_CashbackOrder_Select";
    public static final String SAVE = "stardb.dbo.UPGMKT_Payback_CashbackOrder_Insert";

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
    private Timestamp useEnableDt;

    @Column(name = "SITE_TYPE")
    private String siteType;

    @Column(name = "SMILE_CLUB_YN")
    private String smileClubYn;

    @Column(name = "SHOP_TYPE")
    private String shopType;

    @Column(name = "REG_ID")
    private String regId;

    @Column(name = "REG_DT")
    private Timestamp regDt;

    @Column(name = "CHG_ID")
    private String chgId;
}
