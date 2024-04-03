package com.ebaykorea.payback.schedulercluster.client.dto.smilecash;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Optional;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class MassSaveResponseDto {
  private SmileCashResultBase resultBase;
  public boolean isSuccess() {
    return Optional.ofNullable(resultBase)
        .map(SmileCashResultBase::isSuccess)
        .orElse(false);
  }

  public boolean isFailed() {
    return !isSuccess();
  }

  public String getReturnCode() {
    return Optional.ofNullable(resultBase)
        .map(SmileCashResultBase::getReturnCode)
        .orElse("");
  }

  public String getErrorMessage() {
    return Optional.ofNullable(resultBase)
        .map(SmileCashResultBase::getErrorMessage)
        .orElse("");
  }
}
