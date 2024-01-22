package com.ebaykorea.payback.scheduler.client.dto.smilecash;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Optional;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class SaveResultResponseDto {
  private SaveResultDto result;
  private SmileCashResultBase resultBase;
  private boolean isSuccess() {
    return Optional.ofNullable(resultBase)
        .map(SmileCashResultBase::isSuccess)
        .orElse(false);
  }

  public boolean isSaved() {
    return isSuccess() && Optional.ofNullable(result)
        .map(SaveResultDto::isSaved)
        .orElse(false);
  }
}
