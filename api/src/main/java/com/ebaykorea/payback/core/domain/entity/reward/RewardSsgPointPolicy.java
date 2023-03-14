package com.ebaykorea.payback.core.domain.entity.reward;

import java.math.BigDecimal;
import lombok.Value;

@Value
public class RewardSsgPointPolicy {
  private long policyKey;
  private BigDecimal pointExpectSaveAmount;
  private Boolean isSsgPoint;
  private String expectSaveDate;
}
