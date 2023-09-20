package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@IdClass(SsgPointTargetEntityId.class)
@Table(schema = "O_PAYREWARD", name = "POST_ACCUMULATION")
public class PostAccumulationEntity extends BaseEntity {
  @Id
  @Column(name = "REQUEST_ID")
  private String requestId;

  @Column(name = "USER_TOKEN")
  private String userToken;

  @Column(name = "TOTAL_BENEFIT_AMOUNT")
  private BigDecimal totalBenefitAmount;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumns({
      @JoinColumn(name = "REQUEST_ID", updatable = false)
  })
  private List<PostAccumulationDetailEntity> details;
}
