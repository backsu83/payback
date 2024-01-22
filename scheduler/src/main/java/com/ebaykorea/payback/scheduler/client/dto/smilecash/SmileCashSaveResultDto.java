package com.ebaykorea.payback.scheduler.client.dto.smilecash;

import com.ebaykorea.payback.scheduler.support.SchedulerUtils;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class SmileCashSaveResultDto {
  private BigDecimal amount;
  private String shopManageCode;
  private String shopComment;
  private String expirationDate;
  private String transactionDate;

  public boolean hasAmount() {
    return Optional.ofNullable(amount)
        .map(SchedulerUtils::isGreaterThanZero)
        .orElse(false);
  }
}
