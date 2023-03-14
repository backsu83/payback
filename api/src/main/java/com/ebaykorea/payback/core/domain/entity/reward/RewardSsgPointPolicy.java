package com.ebaykorea.payback.core.domain.entity.reward;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RewardSsgPointPolicy {
  private long policyKey;
  private BigDecimal pointExpectSaveAmount;
  private Boolean isSsgPoint;
  private String expectSaveDate;
}
