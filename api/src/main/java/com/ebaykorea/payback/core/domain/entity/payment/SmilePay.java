package com.ebaykorea.payback.core.domain.entity.payment;

import com.ebaykorea.payback.core.domain.constant.PaymentType;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.exception.PaybackExceptionCode;
import com.ebaykorea.payback.util.PaybackDecimals;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableList;

@Value
public class SmilePay {

    String certificationId;
    String smilePayToken;
    BigDecimal totalMoney;
    BigDecimal cardRequestMoney;
    BigDecimal cashRequestMoney;
    BigDecimal mobileRequestMoney;
    BigDecimal etcRequestMoney;
    BigDecimal ePrepayRequestMoney;
    Boolean isFreeInstallment;
    Long settleGroupSequence;
    List<Long> smilePayContractCode;
    Integer smilePayItemType;

    public Optional<BigDecimal> findCardRequestMoney(){
        return Optional.ofNullable(cardRequestMoney);
    }
    public Optional<BigDecimal> findCashRequestMoney(){
        return Optional.ofNullable(cashRequestMoney);
    }
    public Optional<BigDecimal> findMobileRequestMoney(){
        return Optional.ofNullable(mobileRequestMoney);
    }
    public Optional<BigDecimal> findEtcRequestMoney(){
        return Optional.ofNullable(etcRequestMoney);
    }
    public Optional<BigDecimal> findEPrepayRequestMoney(){
        return Optional.ofNullable(ePrepayRequestMoney);
    }

    public List<PaymentType> toSmilepayPaymentTypes() {
        /**
         * 스마일캐시충전결제 일땐 주결제수단 2개(SG+SE) 케이스가 생김
         */
        final var smilepayPaymentTypes = Stream.of(
                        findCardRequestMoney().filter(PaybackDecimals::isGreaterThanZero).map(m -> PaymentType.NewSmilePayCard).stream(),
                        findCashRequestMoney().filter(PaybackDecimals::isGreaterThanZero).map(m ->PaymentType.NewSmilePayCash).stream(),
                        findMobileRequestMoney().filter(PaybackDecimals::isGreaterThanZero).map(m -> PaymentType.NewSmilePayMobile).stream(),
                        findEtcRequestMoney().filter(PaybackDecimals::isGreaterThanZero).map(m -> PaymentType.NewSmilePayEtc).stream(),
                        findEPrepayRequestMoney().filter(PaybackDecimals::isGreaterThanZero).map(m -> PaymentType.NewSmilePayEPrePay).stream())
                .flatMap(Function.identity())
                .collect(toUnmodifiableList());

        if (smilepayPaymentTypes.isEmpty()){
            throw new PaybackException(PaybackExceptionCode.GATEWAY_001, "fail to smilepayPaymentTypes");
        }

        return smilepayPaymentTypes;
    }
}
