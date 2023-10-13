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
@IdClass(EventRewardRequestStatusEntityId.class)
@Table(schema = "O_PAYREWARD", name = "EVENT_REWARD_REQUEST_STATUS")
public class EventRewardRequestStatusEntity extends BaseEntity {
  @Id
  @Column(name = "EV_RWRD_SEQ")
  private Long requestNo;

  @Id
  @Column(name = "EVENT_REQUEST_STATUS")
  @Enumerated(EnumType.STRING)
  private EventRequestStatusType eventRequestStatus;

}
