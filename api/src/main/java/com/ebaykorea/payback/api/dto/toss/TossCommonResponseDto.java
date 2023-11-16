package com.ebaykorea.payback.api.dto.toss;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TossCommonResponseDto {
  private String resultType;
  private TossEventRewardResponseDto result;
  private String errorCode;
  private String errorMessage;

}