package com.ebaykorea.payback.api.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashbackResponse extends CommonResponse {
  private String message;

  public CashbackResponse(String message , Object data) {
    super(data);
    this.message = message;
  }

  public CashbackResponse(Object data) {
    super(data);
  }
}
