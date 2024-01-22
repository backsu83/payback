package com.ebaykorea.payback.scheduler.client.dto.smilecash;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Optional;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class SmileCashResponseDto {
  private SmileCashResultBase resultBase;
  public boolean isSuccess() {
    return Optional.ofNullable(resultBase)
        .map(SmileCashResultBase::isSuccess)
        .orElse(false);
  }
}
