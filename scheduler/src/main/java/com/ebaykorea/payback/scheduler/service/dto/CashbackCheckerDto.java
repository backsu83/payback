package com.ebaykorea.payback.scheduler.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CashbackCheckerDto {
  private String orderKey;
  private String txKey;
}
