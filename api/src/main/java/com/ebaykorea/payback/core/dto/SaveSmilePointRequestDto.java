package com.ebaykorea.payback.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveSmilePointRequestDto {
  @NotEmpty
  private String buyerNo;


  private int pointAmount;


  private int reasonCode;

  private String comment;

  private long contrNo;

  private int easNo;

  private int eventId;

  private long winNo;

  private String sellerId;
}
