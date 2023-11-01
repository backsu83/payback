package com.ebaykorea.payback.core.domain.constant;

import static com.ebaykorea.payback.util.PaybackStrings.isBlank;

public enum EventRequestStatusType {
  Unknown,
  Created,
  Requested,
  RequestFailed;

  public static EventRequestStatusType getStatusBySmilePayNo(final String smilePayNo) {
    return isBlank(smilePayNo) ? RequestFailed : Requested;
  }
}
