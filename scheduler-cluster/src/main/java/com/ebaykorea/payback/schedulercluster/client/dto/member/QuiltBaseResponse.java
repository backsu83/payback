package com.ebaykorea.payback.schedulercluster.client.dto.member;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
