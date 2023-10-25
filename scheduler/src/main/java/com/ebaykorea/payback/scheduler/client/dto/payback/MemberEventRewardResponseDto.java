package com.ebaykorea.payback.scheduler.client.dto.payback;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
