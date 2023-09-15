package com.ebaykorea.payback.core.dto.member;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemberCashbackResponseDto {
  private String memberKey;
  private List<MemberCashbackResultDto> cashbackResults;
}
