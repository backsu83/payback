package com.ebaykorea.payback.infrastructure.query.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashbackOrderQueryData {
  private String cashbackType;
  private BigDecimal amount;
  private Instant expectSaveDate;
}
