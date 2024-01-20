package com.ebaykorea.payback.scheduler.client.dto.smilecash;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class SmileCashAuthDto {
  private String cashCode;
  private BigDecimal amount;
  private String shopManageCode;
  private String shopComment;
  private String expirationDate;
}
