package com.ebaykorea.payback.core.domain.entity.payment;

import com.ebaykorea.payback.core.domain.constant.PaymentType;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.exception.PaybackExceptionCode;
import lombok.*;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    /** 결제 순번 **/
    Long paymentSequence;
    /** 거래 키 **/
    String txKey;
    /** 구매자 회원 번호 **/
    String buyerNo;
    /** 구매자 회원 아이디 **/
    String buyerId;
    /** 제휴사 코드 **/
    String partnershipCode;
    /** main 결제 코드 */
    PaymentMethod mainPaymentMethod;
    /** sub 결제 코드 */
    List<PaymentMethodSub> subPaymentMethods;

    /** 인증 수단 */
    SmilePay smilePay;

    /** main 결제 타입 */
    PaymentType mainPaymentType;

    /**
     * 대표 결제수단 변환
     * 1. 부 결제수단이 1개 이상 결제된 경우 복합결제 (AX)
     * 2. 스마일페이는 AX
     * 3. 주 결제수단 전액은 주결제수단 타입
     * @return 스마일캐시 전액 또는 스마일페이 전액 또는 복합결제: Complex(AX), 그외 케이스 각 PaymentType
     */
    public PaymentType toPaymentType() {

        if (subPaymentMethods != null && !subPaymentMethods.isEmpty()) {
            return PaymentType.Complex;
        }

        if (mainPaymentType == PaymentType.NewSmilePay) {
            return PaymentType.Complex;
        } else if (mainPaymentType == PaymentType.Unknown) {
            throw new PaybackException(PaybackExceptionCode.GATEWAY_001, "unknown paymentType");        }

        return mainPaymentType;
    }

    /**
     * 주결제정보 및 인증값 기준으로 주결제수단 PaymentType 변환
     * @return 스마일페이 인 경우 AX가 아닌 스마일페이 상세 PaymentType (SR, SH, SM, SE 등), 부결제수단 전액인 경우 Unknown
     */
    public List<PaymentType> toDetailedMainPaymentTypes(List<PaymentType> types) {

        if (mainPaymentType == PaymentType.NewSmilePay) {
            return types;
        } else {
            return List.of(mainPaymentType);
        }
    }

}
