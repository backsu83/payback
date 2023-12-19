package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReferenceType {
  Unknown(null),
  Core("Core"),
  Tour("Tour");

  private final String name;

}
