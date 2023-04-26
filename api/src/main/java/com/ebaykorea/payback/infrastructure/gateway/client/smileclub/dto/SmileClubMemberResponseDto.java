package com.ebaykorea.payback.infrastructure.gateway.client.smileclub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class SmileClubMemberResponseDto {
  private Boolean smileClubMember;
  private Long unifyMasterId;
  SmileClubMemberInfoDto smileClubMemberInformation;
}
