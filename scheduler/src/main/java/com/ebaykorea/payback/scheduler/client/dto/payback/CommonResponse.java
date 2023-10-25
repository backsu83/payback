package com.ebaykorea.payback.scheduler.client.dto.payback;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
  private int code;
  private T data;
  private String message;

  public Optional<T> findSuccessData() {
    return isSuccess() ? Optional.ofNullable(data) : Optional.empty();
  }

  private boolean isSuccess() {
    return code == 200;
  }
}