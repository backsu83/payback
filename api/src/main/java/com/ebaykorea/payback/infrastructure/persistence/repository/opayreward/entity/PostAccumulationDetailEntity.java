package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity;


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
@IdClass(SsgPointTargetEntityId.class)
@Table(schema = "O_PAYREWARD", name = "POST_ACCUMULATION_DETAIL")
public class PostAccumulationDetailEntity extends BaseEntity {
  @Id
  @Column(name = "REQUEST_ID")
  private String requestId;

  @Id
  @Column(name = "APPROVAL_NO")
  private String approvalNo;

  @Column(name = "PAY_AMOUNT")
  private BigDecimal payAmount;

  @Column(name = "CARD_NO")
  private String cardNo;

  @Column(name = "CORPORATE_REG_NO")
  private String corporateRegNo;

  @Column(name = "TRANSACT_AT")
  private Instant transactAt;
}
