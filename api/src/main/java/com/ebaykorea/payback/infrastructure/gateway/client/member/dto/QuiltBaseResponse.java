package com.ebaykorea.payback.infrastructure.gateway.client.member.dto;

import lombok.Value;

import java.util.Optional;

@Value
public class QuiltBaseResponse<T> {
  int resultCode;
  String message;
  T data;

  public Optional<T> findSuccessData() {
    return isSuccess() ? Optional.ofNullable(data) : Optional.empty();
  }

  private boolean isSuccess() {
    return resultCode == 0;
  }
}
