package com.ebaykorea.payback.core.domain.entity.reward;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RewardSsgPointPolicy {
  long policyKey;
  BigDecimal pointExpectSaveAmount;
  Boolean isSsgPoint;
  Integer policyDay;

  public Optional<Integer> findPolicyDay() {
    return Optional.ofNullable(policyDay);
  }
}
