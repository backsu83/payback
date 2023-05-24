package com.ebaykorea.payback.consumer.repository.opayreward.entity;

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
public class CancelConsumerFailEntity extends BaseEntity {

  @Id
  @Column(name = "ORDER_NO")
  private Long orderNo;

  @Id
  @Column(name = "SITE_CODE")
  private String siteType;

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
  private Long tryCnt;
}
