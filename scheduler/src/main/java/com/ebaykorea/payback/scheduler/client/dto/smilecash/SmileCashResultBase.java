package com.ebaykorea.payback.scheduler.client.dto.smilecash;

import static com.ebaykorea.payback.scheduler.support.SchedulerUtils.orElse;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class SmileCashResultBase {
  private String returnCode;
  private String errorMessage;

  private static final String SUCCESS_CODE = "0000";

  public boolean isSuccess() {
    return orElse(returnCode, "").equals(SUCCESS_CODE);
  }
}
