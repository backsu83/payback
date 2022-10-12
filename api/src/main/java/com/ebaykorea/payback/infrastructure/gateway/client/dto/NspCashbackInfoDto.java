package com.ebaykorea.payback.infrastructure.gateway.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class NspCashbackInfoDto {
  /** 스마일페이 캐시백 */
  private Integer payAmount;
  /** 스마일클럽 캐시백 */
  private Integer clubAmount;
  /** 스마일캐시선불충전 캐시백 */
  private Integer autoChargeAmount;
  /** 스마일캐시선불충전(클럽) 캐시백 */
  private Integer autoChargeClubAmount;
}
