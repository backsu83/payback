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
@IdClass(EventSaveRequestEntityId.class)
@Table(schema = "O_PAYREWARD", name = "EVENT_SAVE_REQUEST")
public class EventSaveRequestEntity extends BaseEntity {
  @Id
  @Column(name = "REQUEST_ID")
  private String requestId;

  @Id
  @Column(name = "EVENT_TYPE")
  @Enumerated(EnumType.STRING)
  private EventType eventType;

  @Column(name = "USER_TOKEN")
  private String userToken;

  @Column(name = "TOTAL_SAVE_AMOUNT")
  private BigDecimal totalSaveAmount;

}
