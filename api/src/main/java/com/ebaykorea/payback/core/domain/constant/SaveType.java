package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SaveType {
  Unknown(""),
  Normal("G3"),
  Toss("TODO"), //TODO
  Linkrew("TODO2");//TODO

  private final String code;
}
