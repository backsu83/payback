package com.ebaykorea.payback.scheduler.repository.opayreward.entity.event;

import com.ebaykorea.payback.scheduler.domain.constant.EventRequestStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRewardRequestStatusEntityId implements Serializable {
  private Long requestNo;
  private EventRequestStatusType eventRequestStatus;
}
