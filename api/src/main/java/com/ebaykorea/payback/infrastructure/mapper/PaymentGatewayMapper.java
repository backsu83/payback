package com.ebaykorea.payback.infrastructure.mapper;

import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.infrastructure.gateway.client.payment.dto.PaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentGatewayMapper {

    @Mapping(source = "paymentSequence", target = "paymentSequence")
    @Mapping(source = "txKey", target = "txKey")
    @Mapping(source = "buyerNo", target = "buyerNo")
    @Mapping(source = "buyerId", target = "buyerId")
    @Mapping(source = "partnershipCode", target = "partnershipCode")
    @Mapping(source = "mainPaymentMethod", target = "mainPaymentMethod")
    @Mapping(source = "subPaymentMethods", target = "subPaymentMethods")
    @Mapping(source = "authentications.smilePay", target = "smilePay")
    @Mapping(expression = "java(PaymentMethod.toMainPaymentType("
            + "paymentDto.getMainPaymentMethod().getMediumCode() ,"
            + "paymentDto.getMainPaymentMethod().getAmount()))",  target = "mainPaymentType")
    Payment map(PaymentDto paymentDto);
}
