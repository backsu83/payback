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

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(schema = "O_PAYREWARD", name = "EVENT_REWARD_REQUEST")
public class EventRewardRequestEntity extends BaseEntity {
  @Id
  @Column(name = "REQUEST_NO")
  private Long requestNo;

  @Column(name = "REQUEST_ID")
  private String requestId;

  @Column(name = "EVENT_TYPE")
  @Enumerated(EnumType.STRING)
  private EventType eventType;

  @Column(name = "USER_TOKEN")
  private String userToken;

  @Column(name = "SAVE_AMOUNT")
  private BigDecimal saveAmount;

  @Column(name = "MESSAGE")
  private String message;

  @Column(name = "TENANT_ID")
  private String tenantId;

}
