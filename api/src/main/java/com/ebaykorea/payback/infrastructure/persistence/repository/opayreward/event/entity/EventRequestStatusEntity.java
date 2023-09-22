package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity;

import com.ebaykorea.payback.core.domain.constant.EventRequestStatusType;
import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@IdClass(EventRequestStatusEntityId.class)
@Table(schema = "O_PAYREWARD", name = "EVENT_REQUEST_STATUS")
public class EventRequestStatusEntity extends BaseEntity {
  @Id
  @Column(name = "REQUEST_ID")
  private String requestId;

  @Id
  @Column(name = "EVENT_TYPE")
  @Enumerated(EnumType.STRING)
  private EventType eventType;

  @Id
  @Column(name = "EVENT_REQUEST_STATUS")
  @Enumerated(EnumType.STRING)
  private EventRequestStatusType eventRequestStatus;

}
