package com.ebaykorea.payback.batch.repository.opayreward.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "SEQ_POINT_DAILY_VERIFI",
        sequenceName = "SEQ_SS_POINT_DAILY_VERIFI",
        allocationSize = 1
)
@Table(schema = "O_PAYREWARD", name = "SSG_POINT_DAILY_VERIFY")
public class SsgPointDailyVerifyEntity {
    @Id
    @Column(name = "SSG_POINT_DAILY_VERIFY_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_POINT_DAILY_VERIFI")
    private long ssgPointDailyVerifySeq;

    @Column(name = "TRADE_DATE")
    @NotNull
    private String tradeDate;

    @Column(name = "SITE_TYPE")
    @NotNull
    private String siteType;

    @Column(name = "TRADE_TYPE")
    @NotNull
    private String tradeType;

    @Column(name = "COUNT")
    @NotNull
    private Long count;

    @Column(name = "AMOUNT")
    @NotNull
    private BigDecimal amount;

    @Column(name = "RETURN_CODE")
    private String returnCode;

    @Column(name = "RETURN_MSG")
    private String returnMessage;

    @Column(name = "INS_OPRT")
    private String insertOperator;

    @Column(name = "INS_DATE")
    private Instant insertDate;

    @Column(name = "UPD_OPRT")
    private String updateOperator;

    @Column(name = "UPD_DATE")
    private Instant updateDate;
}
