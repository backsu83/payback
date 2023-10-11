package com.ebaykorea.payback.core.domain.constant;

import static com.ebaykorea.payback.util.PaybackStrings.isBlank;

public enum EventRequestStatusType {
  Unknown,
  Created,
  Requested,
  RequestFailed;

  public static EventRequestStatusType getStatusBySaveProcessId(final String saveProcessId) {
    return isBlank(saveProcessId) ? RequestFailed : Requested;
  }
}
