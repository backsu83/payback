package com.ebaykorea.payback.scheduler.client.dto.payback;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRewardResultDto {
  private long requestNo;
  private long rewardNo;
  private int resultCode;

  @JsonIgnore
  public boolean isSuccess() {
    return resultCode == 0 && rewardNo > 0L;
  }
}
