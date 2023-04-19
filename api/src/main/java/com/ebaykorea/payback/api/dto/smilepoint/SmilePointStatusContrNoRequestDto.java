package com.ebaykorea.payback.api.dto.smilepoint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmilePointStatusContrNoRequestDto {
  @NotEmpty
  private String buyerNo;

  private long contrNo;
}
