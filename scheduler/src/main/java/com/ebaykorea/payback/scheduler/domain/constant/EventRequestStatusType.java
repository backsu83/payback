package com.ebaykorea.payback.scheduler.domain.constant;

import org.springframework.util.StringUtils;

public enum EventRequestStatusType {
  Unknown,
  Created,
  Requested,
  RequestFailed;

  public static EventRequestStatusType getStatusBySaveProcessId(final String saveProcessId) {
    return StringUtils.hasLength(saveProcessId) ? RequestFailed : Requested;
  }
}