package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity;


import com.ebaykorea.payback.core.domain.constant.EventType;
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

  @Column(name = "EVENT_REQUEST_NO")
  private Long eventRequestNo;

  @Column(name = "DETAIL_ID")
  private String detailId; //approvalNo

  @Column(name = "EVENT_AMOUNT")
  private BigDecimal eventAmount; //payAmount

  @Column(name = "ENC_CARD_NO")
  private String encryptedCardNo;

  @Column(name = "ENC_CORPORATE_REG_NO")
  private String encryptedCorporateRegNo;

  @Column(name = "EVENT_DATE")
  private Instant eventDate; //transactAt
}
