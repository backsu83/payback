package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmilePointTradeEntity {
    public static final String SAVE = "tiger.dbo.GMKT_CUSTOMER_SMILEPOINT_TRADE_INSERT";
    public static final String SelectBySmilePayNo = "tiger.dbo.GMKT_CUSTOMER_SMILEPOINT_TRADE_SELECT";
    public static final String SelectByContrNo = "tiger.dbo.GMKT_CUSTOMER_SMILEPOINT_TRADE_SELECT_CONTR_NO";
    public static final String SelectHistory = "tiger.dbo.GMKT_CUSTOMER_SMILEPOINT_TRADE_SELECT_CUST_DATE";

    @Id
    @Column(name = "SMILEPAY_NO")
    private long smilePayNo;

    @Column(name = "CUST_NO")
    private String custNo;

    @Column(name = "CONTR_NO")
    private long contrNo;

    @Column(name = "POINT")
    private int point;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "REASON_CD")
    private int reasonCode;

    @Column(name = "REFUND_REASON_CD")
    private int refundReasonCode;

    @Column(name = "ERS_NO")
    private int ersNo;

    @Column(name = "EID")
    private int eId;

    @Column(name = "WIN_NO")
    private long winNo;

    @Column(name = "ISSUER")
    private int issuer;

    @Column(name = "ISSUER_ID")
    private String issuer_id;

    @Column(name = "REG_DT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String regDt;

    @Column(name = "REG_ID")
    private String regId;

    @Column(name = "CHG_DT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String chgDt;

    @Column(name = "CHG_ID")
    private String chgId;

    @Column(name = "CERT_APPR_ID")
    private String certApprId;

    @Column(name = "SMILE_TOKEN")
    private String smileToken;

    @Column(name = "SERVICE_TYPE")
    private String serviceType;

    @Column(name = "CERT_CD")
    private String certCode;

    @Column(name = "CERT_CLASS")
    private String certClass;

    @Column(name = "SMILEPOINT_CD")
    private String smilePointCode;

    @Column(name = "RET_CD")
    private String returnCode;

    @Column(name = "RET_VALUE")
    private String returnValue;

    @Column(name = "ERROR_MSG")
    private String errorMassage;

    @Column(name = "ACQUIRE_DT")
    private Instant acquireDate;

    @Column(name = "ORG_CERT_APPR_ID")
    private String orgCertApprId;

    @Column(name = "ORG_SMILEPAY_NO")
    private long orgSmilePayNo;

    @Column(name = "CANCEL_CD")
    private String cancelCode;

    @Column(name = "PLUS_MONEY")
    private BigDecimal plusMoney;

    @Column(name = "MINUS_MONEY")
    private BigDecimal minusMoney;

    @Column(name = "CANCEL_DT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String cancelDate;

    @Column(name = "USER_KEY")
    private String userKey;

    @Column(name = "EXPIRE_DT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String expireDate;

    @Column(name = "RET_EXPIRE_DT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String returnExpireDate;

    @Column(name = "ORG_MONEY")
    private BigDecimal orgMoney;

    @Column(name = "CANCEL_MONEY")
    private BigDecimal cancelMoney;

    @Column(name = "CANCEL_PLUS_MONEY")
    private BigDecimal cancelPlusMoney;

    @Column(name = "CANCEL_MINUS_MONEY")
    private BigDecimal cancelMinusMoney;

    @Column(name = "REMAIN_MONEY")
    private BigDecimal remainMoney;

    @Column(name = "REMAIN_PLUS_MONEY")
    private BigDecimal remainPlusMoney;

    @Column(name = "REMAIN_MINUS_MONEY")
    private BigDecimal remainMinusMoney;

    @Column(name = "CERT_DT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String certDate;

    @Column(name = "CERT_ID")
    private String certId;

    @Column(name = "APPR_DT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String apprDate;

    @Column(name = "APPR_ID")
    private String apprId;

    @Column(name = "APPR_STATUS")
    private int apprStatus;

    @Column(name = "TARGET_TYPE")
    private int targetType;

    @Column(name = "DAEMON_EXEC_DT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String daemonExecDate;

    @Column(name = "DAEMON_EXEC_CNT")
    private int daemonExecCount;

    @Column(name = "ETC_COMMENT")
    private String etcComment;

    @Column(name = "EXPIRED_MONEY")
    private BigDecimal expiredMoney;

    @Column(name = "SUB_SHOP_ID")
    private String subShpoId;
}
