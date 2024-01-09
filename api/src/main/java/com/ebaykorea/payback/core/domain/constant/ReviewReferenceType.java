package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewReferenceType {
  Unknown(null),
  Core(100L),
  Moana(101L);

  private final Long code;

}
