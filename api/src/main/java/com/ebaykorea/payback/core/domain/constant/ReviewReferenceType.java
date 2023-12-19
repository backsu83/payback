package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReviewReferenceType {
  Unknown(null),
  Core("Core"),
  Tour("Tour");

  private final String code;

}
