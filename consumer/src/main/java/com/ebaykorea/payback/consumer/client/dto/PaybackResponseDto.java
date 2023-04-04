package com.ebaykorea.payback.consumer.client.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaybackResponseDto {
  private int code;
  private String message;

  public boolean isNotSuccess() { return code != HttpStatus.OK.value() && code != HttpStatus.CREATED.value();}
}
