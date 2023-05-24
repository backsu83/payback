package com.ebaykorea.payback.scheduler.repository.opayreward.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CancelConsumerFailEntityId.class)
@Table(schema = "O_PAYREWARD", name = "CANCEL_CONSUMER_FAIL")
public class CancelConsumerFailEntity {

    @Id
    @Column(name = "SITE_CODE")
    private String siteCode;

    @Id
    @Column(name = "ORDER_NO")
    private Long orderNo;

    @Column(name = "PACK_NO")
    private Long packNo;

    @Column(name = "BUYER_ID")
    private String buyerId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "RSP_CODE")
    private String responseCode;

    @Column(name = "RSP_MSG")
    private String responseMessage;

    @Column(name = "TRY_CNT")
    private Long tryCount;

    @Column(name = "INS_OPRT")
    private String insertOperator;

    @Column(name = "INS_DATE")
    private Instant insertDate;

    @Column(name = "UPD_OPRT")
    private String updateOperator;

    @Column(name = "UPD_DATE")
    private Instant updateDate;
}
