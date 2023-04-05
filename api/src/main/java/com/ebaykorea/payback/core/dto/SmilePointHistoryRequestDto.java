package com.ebaykorea.payback.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmilePointHistoryRequestDto {
  @NotEmpty
  private String buyerNo;

  @NotEmpty
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private String startDate;

  @NotEmpty
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private String endDate;

  private int maxRowCount;
}
