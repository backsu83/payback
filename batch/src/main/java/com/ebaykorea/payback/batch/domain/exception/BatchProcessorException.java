package com.ebaykorea.payback.batch.domain.exception;

import lombok.Getter;

@Getter
public class BatchProcessorException extends RuntimeException {
  private BatchProcessorExceptionCode code;
  private String message;

  public BatchProcessorException(String message) {
    super(message);
  }

  public BatchProcessorException(BatchProcessorExceptionCode paybackMessageType ) {
    this.code = paybackMessageType;
    this.message = paybackMessageType.toString();
  }

}
