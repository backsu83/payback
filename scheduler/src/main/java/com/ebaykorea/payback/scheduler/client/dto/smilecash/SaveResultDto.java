package com.ebaykorea.payback.scheduler.client.dto.smilecash;

import com.ebaykorea.payback.scheduler.support.SchedulerUtils;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Optional;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class SaveResultDto {
  private String authorizationId;
  private String shopTransactionId;
  private SmileCashSaveResultDto smileCash;

  private boolean hasAuthorizationId() {
    return Optional.ofNullable(authorizationId)
        .map(SchedulerUtils::isNotBlank)
        .orElse(false);
  }

  private boolean hasSmileCash() {
    return Optional.ofNullable(smileCash)
        .map(SmileCashSaveResultDto::hasAmount)
        .orElse(false);
  }
  public boolean isSaved() {
    return hasAuthorizationId() && hasSmileCash();
  }
}
