package com.ebaykorea.payback.schedulercluster.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GmarketSmileCashEventType {
  Unknown(""),
  A001("GiftCard"),
  A002("Save"),
  A003("SaveEvent"),
  A004("CSSave"),
  A005("CSSaveEvent");

  private final String name;
}
