package com.ebaykorea.payback.core.domain.entity.payment;

import com.ebaykorea.payback.core.exception.PaybackException;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_001;
import static com.ebaykorea.payback.util.PaybackCollections.orEmptyStream;
import static com.ebaykorea.payback.util.PaybackDecimals.isGreaterThanZero;

@Value
public class Payment {
  /**
   * 결제 순번
   **/
  Long paymentSequence;
  /**
   * main 결제 코드
   */
  PaymentMethod mainPaymentMethod;
  /**
   * sub 결제 코드
   */
  List<PaymentMethodSub> subPaymentMethods;

  SmilePay smilePay;

  Card card;

  public static Payment of(
      final Long paymentSequence,
      final PaymentMethod mainPaymentMethod,
      final List<PaymentMethodSub> subPaymentMethods,
      final SmilePay smilePay,
      final Card card
  ) {
    return new Payment(paymentSequence, mainPaymentMethod, subPaymentMethods, smilePay, card);
  }

  private Payment(
      final Long paymentSequence,
      final PaymentMethod mainPaymentMethod,
      final List<PaymentMethodSub> subPaymentMethods,
      final SmilePay smilePay,
      final Card card
  ) {
    this.paymentSequence = paymentSequence;
    this.mainPaymentMethod = mainPaymentMethod;
    this.subPaymentMethods = subPaymentMethods;
    this.smilePay = smilePay;
    this.card = card;

    validate();
  }

  private void validate() {
    if (!hasMainPaymentMethod() && !hasSubPaymentMethods()) {
      throw new PaybackException(DOMAIN_ENTITY_001, "결제수단이 없습니다");
    }
  }

  private Optional<PaymentMethod> findMainPaymentMethod() {
    return Optional.ofNullable(mainPaymentMethod);
  }

  // 주 결제 수단 존재 여부
  private boolean hasMainPaymentMethod() {
    return findMainPaymentMethod().isPresent();
  }

  // 부 결제 수단 존재 여부
  private boolean hasSubPaymentMethods() {
    return orEmptyStream(subPaymentMethods).findAny().isPresent();
  }

  public BigDecimal getMainPaymentAmount() {
    return findMainPaymentMethod()
        .map(PaymentMethod::getAmount)
        .orElse(BigDecimal.ZERO);
  }

  private Optional<Card> findCard() {
    return Optional.ofNullable(card);
  }

  // 수기 결제 여부
  public boolean isManualCardPayment() {
    return findCard()
        .map(Card::isManualPayment)
        .orElse(false);
  }

  // 무이자 할부 여부
  public boolean isFreeInstallment() {
    return isCardFreeInstallment() || isSmilePayFreeInstallment();
  }

  private boolean isCardFreeInstallment() {
    return findCard()
        .map(Card::isFreeInstallment)
        .orElse(false);
  }

  private boolean isSmilePayFreeInstallment() {
    return Optional.ofNullable(smilePay)
        .map(SmilePay::isFreeInstallment)
        .orElse(false);
  }
}
