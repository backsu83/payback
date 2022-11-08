package com.ebaykorea.payback.core.domain.constant;

public class PaymentCode {
  public static class PaymentMethodSmallCode {
    public static final String SmileCard = "300000282";
    public static final String SmileCardT1 = "300000287";
    public static final String SmileCardT2 = "300000288";
    public static final String SmileCardT3 = "300000289";
    public static final String SmilePayReCharge = "300000290";
  }

  public static class PaymentMethodMediumCode {
    public static final String Complex = "000000000";
    public static final String WireTransfer = "200000002";
    public static final String Phone = "200000004";
    public static final String PayPal = "200000005";
    public static final String CreditCard = "200000009";
    public static final String ForeignCreditCard = "200000012";
    public static final String Direct = "200000015";
    public static final String AliPay = "200000028";
    public static final String PayOnDelivery = "200000034";
    public static final String NewSmilePayCard = "200000035";
    public static final String NewSmilePayCMS = "200000036";
    public static final String SamsungPay = "200000039";
    public static final String NewSmilePayMobile = "200000040";
  }
}
