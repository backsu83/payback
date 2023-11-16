package com.ebaykorea.payback.core.domain.entity.event;

import lombok.Value;

import java.util.Optional;

import static com.ebaykorea.payback.util.PaybackObjects.orElse;

@Value
public class SmileCashEvent {
  Long smilePayNo;
  boolean saved;
  boolean failed;
  String returnCode;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String IN_PROGRESS = "IN_PROGRESS";

  public String getSmilePayNo() {
    return Optional.ofNullable(smilePayNo)
        .map(String::valueOf)
        .orElse("");
  }

  public String getResultCode() {
    if (!hasSmilePayNo() || failed) {
      return FAILED;
    } else if (saved) {
      return SUCCESS;
    }
    return IN_PROGRESS;
  }

  public String getResultMessage() {
    return getResultCode().equals(FAILED) ? returnCode : null;
  }

  private boolean hasSmilePayNo() {
    return orElse(smilePayNo, 0L) > 0L;
  }
}
