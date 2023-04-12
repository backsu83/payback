package com.ebaykorea.payback.api.dto.smilepoint;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveSmilePointResponseDto<T> {
  private String returnCode;
  private String returnMessage;
  private T data;

  public SaveSmilePointResponseDto(final String returnCode, final String returnMessage, final T data) {
    this.returnCode = returnCode;
    this.returnMessage = returnMessage;
    this.data = data;
  }
}
