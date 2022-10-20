package com.ebaykorea.payback.core.domain.constant;

import com.ebaykorea.payback.util.PaybackEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public enum PaymentType {

    Unknown("", "", CashPayWayType.Unknown),
    Card("A2", "카드", CashPayWayType.CashPayM),
    Cash("A3", "현금", CashPayWayType.VirtualCash),
    FreeFeeCard("A7", "무이자 카드", CashPayWayType.Unknown), //미사용
    GStamp("A8", "G스탬프", CashPayWayType.Unknown), //미사용
    Phone("A9", "휴대폰", CashPayWayType.CashPayM),
    DirectPayment("AD", "직접결제", CashPayWayType.DirectPayment),
    FoodCoupon("AF", "식사쿠폰", CashPayWayType.Unknown), //미사용
    GNewCash("AG", "신G캐시", CashPayWayType.Unknown), //이거도 미사용인듯?
    PartnershipPoint("AP", "제휴사 포인트", CashPayWayType.Unknown), //미사용
    OuterLink("GL", "외부연동", CashPayWayType.Unknown),//미사용
    GlobalPayment("I1", "글로벌결제", CashPayWayType.PayPal),
    AliPay("IA", "알리페이", CashPayWayType.Alipay),
    ContentsPayment("PG", "컨텐츠결제", CashPayWayType.Unknown),//미사용
    SmileCash("Q1", "SmileCash", CashPayWayType.SmileCash),
    SmileCashEvent("Q2", "SmileCashEvent", CashPayWayType.SmileCashEvent),
    NewSmilePayCash("SH", "NewSmilePay", CashPayWayType.NewSmilePayCash),
    NewSmilePayCard("SR", "NewSmilePayCard", CashPayWayType.CashPayR),
    NewSmilePayMobile("SM", "NewSmilePayMobile", CashPayWayType.CashPayM),
    NewSmilePayEtc("SE", "NewSmilePayEtc", CashPayWayType.NewSmilePayEtc),
    NewSmilePayEPrePay("SG", "NewSmilePayEPrePay", CashPayWayType.CashPayG),
    NewSmilePay("SP", "DB에 직접 들어가는 값은 아님. NewSmilePay를 구분하기 용도임", CashPayWayType.Unknown),
    Complex("AX", "복합결제 (항상 마지막으로 설정)", CashPayWayType.Unknown);

    private String code;
    private String description;
    private CashPayWayType cashPayWayType;

    private static final transient Map<String, PaymentType> map = PaybackEnums
            .reverseMap(PaymentType.class, PaymentType::getCode);

    private static final transient Map<String, PaymentType> mediumCodeMap = Map.ofEntries(
            Map.entry(PaymentCode.PaymentMethodMediumCode.WireTransfer, Cash),
            Map.entry(PaymentCode.PaymentMethodMediumCode.Phone, Phone),
            Map.entry(PaymentCode.PaymentMethodMediumCode.SamsungPay, Card),
            Map.entry(PaymentCode.PaymentMethodMediumCode.CreditCard, Card),
            Map.entry(PaymentCode.PaymentMethodMediumCode.ForeignCreditCard, Card),
            Map.entry(PaymentCode.PaymentMethodMediumCode.Direct, DirectPayment),
            Map.entry(PaymentCode.PaymentMethodMediumCode.PayOnDelivery, DirectPayment),
            Map.entry(PaymentCode.PaymentMethodMediumCode.PayPal, GlobalPayment),
            Map.entry(PaymentCode.PaymentMethodMediumCode.AliPay, AliPay),
            Map.entry(PaymentCode.PaymentMethodMediumCode.NewSmilePayCard, NewSmilePay),
            Map.entry(PaymentCode.PaymentMethodMediumCode.NewSmilePayCMS, NewSmilePay),
            Map.entry(PaymentCode.PaymentMethodMediumCode.NewSmilePayMobile, NewSmilePay),
            Map.entry(PaymentCode.PaymentMethodMediumCode.Complex, Complex)
    );

    public static PaymentType forValue(String value) {
        return map.getOrDefault(value.toUpperCase(), Unknown);
    }

    public static PaymentType ofMediumCode(final String mediumCode) {
        return mediumCodeMap.getOrDefault(mediumCode, Unknown);
    }

    public String toValue() {
        return this.code;
    }

    public CashPayWayType toCashPayWayType() {
        return cashPayWayType;
    }
}
