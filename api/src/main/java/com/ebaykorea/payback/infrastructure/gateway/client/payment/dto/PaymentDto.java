package com.ebaykorea.payback.infrastructure.gateway.client.payment.dto;

import com.ebaykorea.payback.core.domain.constant.PaymentType;
import com.ebaykorea.payback.infrastructure.gateway.client.payment.dto.auth.SmilePayDto;
import com.ebaykorea.payback.util.PaybackDecimals;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.ebaykorea.payback.core.domain.constant.PaymentCode.PaymentMethodMediumCode.Direct;
import static java.util.stream.Collectors.toUnmodifiableList;


@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class PaymentDto {
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
    /** main 결제 수단 */
    PaymentMainDto mainPaymentMethod;
    /** sub 결제 수단 */
    List<PaymentSubDto> subPaymentMethods;
    /** 인증 정보 */
    PaymentAuthDto authentications;
//    /** 메타 */
//    MetaDto meta;

    /**
     * 대표 결제수단 변환
     * 1. 부 결제수단이 1개 이상 결제된 경우 복합결제 (AX)
     * 2. 스마일페이는 AX
     * 3. 주 결제수단 전액은 주결제수단 타입
     * @return 스마일캐시 전액 또는 스마일페이 전액 또는 복합결제: Complex(AX), 그외 케이스 각 PaymentType
     */
    @JsonIgnore
    public PaymentType toPaymentType() {

        if (subPaymentMethods != null && !subPaymentMethods.isEmpty()) {
            return PaymentType.Complex;
        }

        final var paymentType = toMainPaymentType();
        if (paymentType == PaymentType.NewSmilePay) {
            return PaymentType.Complex;
        } else if (paymentType == PaymentType.Unknown) {
            throw new PaymentBusinessException("fail to paymentType");        }

        return paymentType;
    }

    /**
     * 주결제정보 및 인증값 기준으로 주결제수단 PaymentType 변환
     * @return 스마일페이 인 경우 AX가 아닌 스마일페이 상세 PaymentType (SR, SH, SM, SE 등), 부결제수단 전액인 경우 Unknown
     */
    @JsonIgnore
    public List<PaymentType> toDetailedMainPaymentTypes() {
        final var paymentType = toMainPaymentType();

        if (paymentType == PaymentType.NewSmilePay) {
            return toSmilepayPaymentTypes(authentications.getSmilePay());
        } else {
            return List.of(paymentType);
        }
    }

    private List<PaymentType> toSmilepayPaymentTypes(final SmilePayDto newSmilePay) {

        if (newSmilePay == null) {
            throw new PaymentBusinessException("newSmilePay is null");
        }

        /**
         * 스마일캐시충전결제 일땐 주결제수단 2개(SG+SE) 케이스가 생김
         */
        final var smilepayPaymentTypes = Stream.of(
                        newSmilePay.findCardRequestMoney().filter(PaybackDecimals::isGreaterThanZero).map(m -> PaymentType.NewSmilePayCard).stream(),
                        newSmilePay.findCashRequestMoney().filter(PaybackDecimals::isGreaterThanZero).map(m ->PaymentType.NewSmilePayCash).stream(),
                        newSmilePay.findMobileRequestMoney().filter(PaybackDecimals::isGreaterThanZero).map(m -> PaymentType.NewSmilePayMobile).stream(),
                        newSmilePay.findEtcRequestMoney().filter(PaybackDecimals::isGreaterThanZero).map(m -> PaymentType.NewSmilePayEtc).stream(),
                        newSmilePay.findEPrepayRequestMoney().filter(PaybackDecimals::isGreaterThanZero).map(m -> PaymentType.NewSmilePayEPrePay).stream())
                .flatMap(Function.identity())
                .collect(toUnmodifiableList());

        if (smilepayPaymentTypes.isEmpty()){
            throw new PaymentBusinessException("fail to smilepayPaymentTypes");
        }

        return smilepayPaymentTypes;
    }

    @JsonIgnore
    public boolean hasMainPayment() {
        return hasMainPaymentAmount() || hasMainPaymentMethod(mainPaymentMediumCode(Direct));
    }

    @JsonIgnore
    private boolean hasMainPaymentAmount() {
        return Optional.ofNullable(mainPaymentMethod)
                .map(PaymentMainDto::hasAmount)
                .orElse(false);
    }

    @JsonIgnore
    public boolean hasMainPaymentMethod(Predicate<PaymentMainDto> predicate) {
        return Optional.ofNullable(mainPaymentMethod).stream().anyMatch(predicate);
    }

    @JsonIgnore
    public static Predicate<PaymentMainDto> mainPaymentMediumCode(final String ...mediumCodes) {
        return mainPayment -> mainPayment.hasMediumCode(mediumCodes);
    }

    @JsonIgnore
    public PaymentType toRawMainPaymentType() {
        return PaymentType.ofMediumCode(mainPaymentMethod.getMediumCode());
    }

    private PaymentType toMainPaymentType() {
        // 부 결제 수단 100%인 경우 (스마일캐시 전액 등) 주 결제수단은 Unknown
        return hasMainPayment()
                ? toRawMainPaymentType()
                : PaymentType.Unknown;
    }

}
