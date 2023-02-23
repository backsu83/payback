package com.ebaykorea.payback.api.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmilePointResponseDto<T> {
  private String returnCode;
  private String returnMessage;
  private T data;

  public SmilePointResponseDto(final String returnCode, final String returnMessage, final T data) {
    this.returnCode = returnCode;
    this.returnMessage = returnMessage;
    this.data = data;
  }
}
