package com.ebaykorea.payback.infrastructure.gateway.client.smileclub.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class SmileClubMemberInfoDto {

  private String statusCode;
  private String membershipGrade;
  private String payCycleType;
  private String joinPartnerId;
  private String memberType;
  private Boolean isUnifyMembership;
  private Boolean isSSGMembership;

}
