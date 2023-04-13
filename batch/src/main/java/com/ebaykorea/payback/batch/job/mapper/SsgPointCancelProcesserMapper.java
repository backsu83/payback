package com.ebaykorea.payback.batch.job.mapper;


import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointCancelRequest;
import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointCommonResponse;
import com.ebaykorea.payback.batch.domain.SsgPointCertifier;
import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto;
import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import com.ebaykorea.payback.batch.util.PaybackDecimals;
import com.ebaykorea.payback.batch.util.PaybackInstants;
import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {
        OrderSiteType.class,
        PointStatusType.class,
        PointTradeType.class,
        BigDecimal.class,
        PaybackInstants.class,
        PaybackDecimals.class
    }
)
public interface SsgPointCancelProcesserMapper {

    @Mapping(source = "certifier.clientId", target = "clientId")
    @Mapping(source = "certifier.apiKey", target = "apiKey")
    @Mapping(source = "certifier.branchId", target = "brchId")
    @Mapping(source = "tokenId", target = "tokenId")
    @Mapping(source = "processerDto.trcNo", target = "reqTrcNo")
    @Mapping(source = "processerDto.receiptNo", target = "recptNo")
    @Mapping(source = "processerDto.tradeNo", target = "tradeNo")
    @Mapping(constant = "APITRN0141", target = "msgText")
    @Mapping(constant = "400080", target = "tradeGbCd")
    @Mapping(expression = "java(PaybackInstants.getStringFormatBy(\"yyyyMMdd\"))", target = "busiDt")
    @Mapping(expression = "java(PaybackInstants.getStringFormatBy(\"MMdd\"))", target = "tradeGentdDt")
    @Mapping(expression = "java(PaybackInstants.getStringFormatBy(\"HHmmss\"))", target = "tradeGentdTm")
    @Mapping(constant = "0000", target = "tradeGentdStcd")
    @Mapping(constant = "0000", target = "tradeGentdPosno")
    @Mapping(constant = "000000", target = "doByid")
    @Mapping(constant = "O", target = "inputFlg")
    @Mapping(source = "cardNo", target = "cardNo")
    @Mapping(constant = "0000", target = "recptSeq")
    @Mapping(source = "processerDto.orderNo", target = "orgSaleTradeNo")
    @Mapping(source = "processerDto.payAmount", target = "otradeTotAmt")      //원거래 총 거래 금액
    @Mapping(source = "processerDto.accountDate", target = "otradeBusiDt")    //원거래영업일자
    @Mapping(source = "processerDto.orgReceiptNo", target = "otradeRecptNo")  //원거래영수증번호
    @Mapping(source = "processerDto.orgPntApprId", target = "otradeApprId")   //원거래포인트승인ID
    SsgPointCancelRequest mapToRequest(SsgPointProcesserDto processerDto , SsgPointCertifier certifier , String tokenId , String cardNo);


    @Mapping(source = "processerDto.orderNo", target = "orderNo")
    @Mapping(source = "processerDto.buyerId", target = "buyerId")
    @Mapping(source = "processerDto.siteType", target = "siteType")
    @Mapping(source = "processerDto.tradeType", target = "tradeType")
    @Mapping(source = "processerDto.status", target = "status")
    @Mapping(source = "response.pntApprId", target = "pntApprId")
    @Mapping(expression = "java(PaybackDecimals.from(response.getGpoint()))", target = "saveAmount")
    @Mapping(source = "response.responseCd", target = "responseCode")
    @Mapping(source = "request.busiDt", target = "accountDate")
    @Mapping(expression = "java(request.getRequestDate())", target = "requestDate")
    SsgPointTargetDto mapToTarget(SsgPointCancelRequest request , SsgPointCommonResponse response ,SsgPointProcesserDto processerDto);

}
