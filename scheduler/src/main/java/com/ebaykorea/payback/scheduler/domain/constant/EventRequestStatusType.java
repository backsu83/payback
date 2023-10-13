package com.ebaykorea.payback.scheduler.domain.constant;

public enum EventRequestStatusType {
  Unknown,
  Created,
  Requested,
  RequestFailed;

  public static EventRequestStatusType getStatus(final boolean isSuccess) {
    return isSuccess ? RequestFailed : Requested;
  }
}