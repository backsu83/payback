package com.ebaykorea.payback.scheduler.repository.opayreward.entity.event;

import com.ebaykorea.payback.scheduler.model.constant.EventRequestStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRewardRequestStatusEntityId implements Serializable {
  private Long requestNo;
  @Enumerated(EnumType.STRING)
  private EventRequestStatusType eventRequestStatus;
}
