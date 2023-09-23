package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity;

import com.ebaykorea.payback.core.domain.constant.EventRequestStatusType;
import com.ebaykorea.payback.core.domain.constant.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRewardRequestStatusEntityId implements Serializable {
  private String requestId;
  private EventType eventType;
  private EventRequestStatusType eventRequestStatus;
}
