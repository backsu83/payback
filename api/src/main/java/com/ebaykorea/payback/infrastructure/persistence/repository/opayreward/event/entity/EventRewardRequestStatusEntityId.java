package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity;

import com.ebaykorea.payback.core.domain.constant.TossRewardRequestStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRewardRequestStatusEntityId implements Serializable {
  private Long requestNo;
  private TossRewardRequestStatusType eventRequestStatus;
}
