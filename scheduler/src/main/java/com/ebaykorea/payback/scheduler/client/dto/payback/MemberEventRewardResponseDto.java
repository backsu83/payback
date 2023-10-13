package com.ebaykorea.payback.scheduler.client.dto.payback;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class MemberEventRewardResponseDto {
  private String memberKey;
  private MemberEventRewardResultDto eventRewardResult;

  @JsonIgnore
  public boolean isSuccess() {
    return Optional.ofNullable(eventRewardResult)
        .map(MemberEventRewardResultDto::isSuccess)
        .orElse(false);
  }
}
