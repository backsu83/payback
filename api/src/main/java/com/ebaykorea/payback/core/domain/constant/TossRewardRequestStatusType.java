package com.ebaykorea.payback.core.domain.constant;

import static com.ebaykorea.payback.util.PaybackStrings.isBlank;

public enum TossRewardRequestStatusType {
  Unknown,
  Created,
  Requested,
  RequestFailed;

  public static TossRewardRequestStatusType getStatusBySmilePayNo(final String smilePayNo) {
    return isBlank(smilePayNo) ? RequestFailed : Requested;
  }
}
