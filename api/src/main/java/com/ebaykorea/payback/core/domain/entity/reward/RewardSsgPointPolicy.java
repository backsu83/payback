package com.ebaykorea.payback.core.domain.entity.reward;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RewardSsgPointPolicy {
  long policyKey;
  BigDecimal pointExpectSaveAmount;
  Boolean isSsgPoint;
  String expectSaveDate;
}
