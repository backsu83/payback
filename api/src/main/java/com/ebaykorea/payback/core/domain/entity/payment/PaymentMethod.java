package com.ebaykorea.payback.core.domain.entity.payment;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

import static com.ebaykorea.payback.core.domain.constant.PaymentCode.PaymentMethodMediumCode.*;
import static com.ebaykorea.payback.core.domain.constant.PaymentCode.PaymentMethodSmallCode.SmilePayReCharge;

@Value
@Builder
public class PaymentMethod {
  /**
   * 결제 금액
   */
  BigDecimal amount;
  /**
   * 중분류코드
   */
  String mediumCode;
  /**
   * 소분류코드
   */
  String smallCode;

  // 스마일페이 여부
  public boolean isSmilePay() {
    return hasMediumCode(NewSmilePayCard, NewSmilePayCMS, NewSmilePayMobile);
  }

  // 캐시 충전 여부
  public boolean isChargePay() {
    return hasSmallCode(SmilePayReCharge);
  }

  private boolean hasMediumCode(final String ...mediumCodes) {
    for (String code : mediumCodes) {
      if (code.equals(mediumCode)) {
        return true;
      }
    }
    return false;
  }

  private boolean hasSmallCode(final String ...smallCodes) {
    for (String code : smallCodes) {
      if (code.equals(smallCode)) {
        return true;
      }
    }
    return false;
  }

}
