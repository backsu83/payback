package com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CashbackOrderPolicyEntityId implements Serializable {

  @Id
  @Column(name = "BUY_ORDER_NO")
  private Long buyOrderNo;

  @Id
  @Column(name = "CASHBACK_TYPE")
  private String cashbackType;

  @Id
  @Column(name = "CASHBACK_POLICY_NO")
  private Long cashbackPolicyNo;

}
