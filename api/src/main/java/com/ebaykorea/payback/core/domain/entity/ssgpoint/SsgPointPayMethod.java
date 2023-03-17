package com.ebaykorea.payback.core.domain.entity.ssgpoint;

import lombok.Value;

@Value
public class SsgPointPayMethod {
  String payType;
  String amount;
  String gubun;
}
