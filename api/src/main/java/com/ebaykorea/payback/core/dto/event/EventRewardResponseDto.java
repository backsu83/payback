package com.ebaykorea.payback.core.dto.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventRewardResponseDto {
  private String memberKey;
  private EventRewardResultDto eventRewardResult;
}
