package com.ebaykorea.payback.core.domain.model.policy;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Policy {
  long policyNo;
  long orderNo;
  CashbackType type;
  String name;
  String subType;
  String payType;
  BigDecimal rate;

  PayPolicy payPolicy;
}
