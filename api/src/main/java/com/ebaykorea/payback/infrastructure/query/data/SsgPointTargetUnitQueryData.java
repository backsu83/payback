package com.ebaykorea.payback.infrastructure.query.data;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

import static com.ebaykorea.payback.util.PaybackObjects.orElse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SsgPointTargetUnitQueryData {
  private BigDecimal amount;
  private Instant expectSaveDate;
  private String pointStatus;

  public boolean isTarget() {
    return !PointStatusType.CancelBeforeSave.getCode().equals(orElse(pointStatus, ""));
  }
}
