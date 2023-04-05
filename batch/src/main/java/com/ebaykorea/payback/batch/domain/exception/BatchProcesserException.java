package com.ebaykorea.payback.batch.domain.exception;

import lombok.Getter;

@Getter
public class BatchProcesserException extends RuntimeException {
  private BatchProcesserExceptionCode code;
  private String message;

  public BatchProcesserException(String message) {
    super(message);
  }

  public BatchProcesserException(BatchProcesserExceptionCode paybackMessageType ) {
    this.code = paybackMessageType;
    this.message = paybackMessageType.toString();
  }

}
