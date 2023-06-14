package com.ebaykorea.payback.batch.job.mapper;


import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointCancelRequest;
import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointCommonResponse;
import com.ebaykorea.payback.batch.domain.SsgPointCertifier;
import com.ebaykorea.payback.batch.domain.SsgPointProcessorDto;
import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import com.ebaykorea.payback.batch.util.PaybackDecimals;
import com.ebaykorea.payback.batch.util.PaybackInstants;
import java.math.BigDecimal;
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
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
public interface SsgPointCancelProcessorMapper {

    @Mapping(source = "certifier.clientId", target = "clientId")
    @Mapping(source = "certifier.apiKey", target = "apiKey")
    @Mapping(source = "certifier.branchId", target = "brchId")
    @Mapping(source = "tokenId", target = "tokenId")
    @Mapping(source = "processorDto.trcNo", target = "reqTrcNo")
    @Mapping(source = "processorDto.receiptNo", target = "recptNo")
    @Mapping(source = "processorDto.tradeNo", target = "tradeNo")
    @Mapping(source = "processorDto.pointToken", target = "cardNo")
    @Mapping(constant = "APITRN0141", target = "msgText")
    @Mapping(constant = "400080", target = "tradeGbCd")
    @Mapping(expression = "java(PaybackInstants.getStringFormatBy(\"yyyyMMdd\"))", target = "busiDt")
    @Mapping(expression = "java(PaybackInstants.getStringFormatBy(\"MMdd\"))", target = "tradeGentdDt")
    @Mapping(expression = "java(PaybackInstants.getStringFormatBy(\"HHmmss\"))", target = "tradeGentdTm")
    @Mapping(constant = "0000", target = "tradeGentdStcd")
    @Mapping(constant = "0000", target = "tradeGentdPosno")
    @Mapping(constant = "000000", target = "doByid")
    @Mapping(constant = "O", target = "inputFlg")
    @Mapping(constant = "0000", target = "recptSeq")
    @Mapping(source = "processorDto.orderNo", target = "orgSaleTradeNo")
    @Mapping(source = "processorDto.payAmount", target = "otradeTotAmt")      //원거래 총 거래 금액
    @Mapping(source = "processorDto.accountDate", target = "otradeBusiDt")    //원거래영업일자
    @Mapping(source = "processorDto.orgReceiptNo", target = "otradeRecptNo")  //원거래영수증번호
    @Mapping(source = "processorDto.orgPntApprId", target = "otradeApprId")   //원거래포인트승인ID
    SsgPointCancelRequest mapToRequest(SsgPointProcessorDto processorDto , SsgPointCertifier certifier , String tokenId);


    @Mapping(source = "processorDto.orderNo", target = "orderNo")
    @Mapping(source = "processorDto.buyerId", target = "buyerId")
    @Mapping(source = "processorDto.siteType", target = "siteType")
    @Mapping(source = "processorDto.tradeType", target = "tradeType")
    @Mapping(source = "processorDto.status", target = "status")
    @Mapping(source = "processorDto.pointToken", target = "pointToken")
    @Mapping(source = "response.pntApprId", target = "pntApprId")
    @Mapping(source = "response.dupApprid", target = "dupApprid", qualifiedByName = "mapDupApprid")
    @Mapping(expression = "java(PaybackDecimals.from(response.getGpoint()))", target = "saveAmount")
    @Mapping(expression = "java(PaybackDecimals.from(response.getDupApoint()))", target = "dupApoint")
    @Mapping(source = "response.responseCd", target = "responseCode")
    @Mapping(source = "busiDt", target = "accountDate")
    @Mapping(source = "requestDate", target = "requestDate")
    SsgPointTargetDto mapToTarget(String busiDt, String requestDate, SsgPointCommonResponse response ,SsgPointProcessorDto processorDto);

    @Named("mapDupApprid")
    default String mapDupApprid(String dupApprid) {
        if(Strings.isEmpty(dupApprid)) {
            return null;
        }
        return dupApprid;
    }
}
