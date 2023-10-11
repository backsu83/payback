package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity;


import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(schema = "O_PAYREWARD", name = "EVENT_REWARD_REQUEST_DETAIL")
public class EventRewardRequestDetailEntity extends BaseEntity {
  @Id
  @Column(name = "SEQ")
  private Long seq;

  @Column(name = "REQUEST_NO")
  private Long requestNo;

  @Column(name = "DETAIL_ID")
  private String detailId;

  @Column(name = "EVENT_AMOUNT")
  private BigDecimal eventAmount; //payAmount

  @Column(name = "EVENT_DATE")
  private Instant eventDate; //transactAt

  @Column(name = "APPROVAL_NO")
  private String cardApprovalNo;

  @Column(name = "MASKED_CARD_NUMBER")
  private String maskedCardNumber;

  @Column(name = "CORPORATE_REG_NO")
  private String corporateRegNo;

}
