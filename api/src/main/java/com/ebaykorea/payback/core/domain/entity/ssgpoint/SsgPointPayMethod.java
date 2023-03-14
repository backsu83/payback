package com.ebaykorea.payback.core.domain.entity.ssgpoint;

import lombok.Value;

@Value
public class SsgPointPayMethod {
  private String payType;
  private String amount;
  private String gubun;
}
