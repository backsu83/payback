package com.ebaykorea.payback.batch.job.mapper;

import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointEarnRequest;
import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointPayInfo;
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
import java.util.List;
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
public interface SsgPointEarnProcessorMapper {

  @Mapping(source = "authInfo.clientId", target = "clientId")
  @Mapping(source = "authInfo.apiKey", target = "apiKey")
  @Mapping(source = "authInfo.branchId", target = "brchId")
  @Mapping(source = "tokenId", target = "tokenId")
  @Mapping(source = "processorDto.trcNo", target = "reqTrcNo")
  @Mapping(source = "processorDto.receiptNo", target = "recptNo")
  @Mapping(source = "processorDto.payAmount", target = "totAmt")
  @Mapping(source = "processorDto.tradeNo", target = "tradeNo")
  @Mapping(constant = "APITRN0121", target = "msgText")
  @Mapping(constant = "200020", target = "tradeGbCd")
  @Mapping(expression = "java(PaybackInstants.getStringFormatBy(\"yyyyMMdd\"))", target = "busiDt")
  @Mapping(expression = "java(PaybackInstants.getStringFormatBy(\"MMdd\"))", target = "tradeGentdDt")
  @Mapping(expression = "java(PaybackInstants.getStringFormatBy(\"HHmmss\"))", target = "tradeGentdTm")
  @Mapping(constant = "0000", target = "tradeGentdStcd")
  @Mapping(constant = "0000", target = "tradeGentdPosno")
  @Mapping(constant = "000000", target = "doByid")
  @Mapping(constant = "O", target = "inputFlg")
  @Mapping(source = "cardNo", target = "cardNo")
  @Mapping(constant = "0000", target = "recptSeq")
  @Mapping(expression = "java(BigDecimal.valueOf(0L))", target = "pntNoAddProdAmt")
  @Mapping(source = "processorDto.orderNo", target = "orgSaleTradeNo")
  @Mapping(source = "processorDto.payAmount" , target = "payInfo" , qualifiedByName = "mapToPayInfo")
  SsgPointEarnRequest mapToRequest(SsgPointProcessorDto processorDto , SsgPointCertifier authInfo , String tokenId , String cardNo);

  @Mapping(source = "processorDto.orderNo", target = "orderNo")
  @Mapping(source = "processorDto.buyerId", target = "buyerId")
  @Mapping(source = "processorDto.siteType", target = "siteType")
  @Mapping(source = "processorDto.tradeType", target = "tradeType")
  @Mapping(source = "processorDto.status", target = "status")
  @Mapping(source = "response.pntApprId", target = "pntApprId")
  @Mapping(source = "response.dupApprid", target = "dupApprid", qualifiedByName = "mapDupApprid")
  @Mapping(expression = "java(PaybackDecimals.from(response.getGpoint()))", target = "saveAmount")
  @Mapping(expression = "java(PaybackDecimals.from(response.getDupApoint()))", target = "dupApoint")
  @Mapping(source = "response.responseCd", target = "responseCode")
  @Mapping(source = "busiDt", target = "accountDate")
  @Mapping(source = "cardNo", target = "pointToken")
  @Mapping(source = "requestDate", target = "requestDate")
  SsgPointTargetDto mapToTarget(String busiDt, String requestDate, String cardNo, SsgPointCommonResponse response ,SsgPointProcessorDto processorDto);

  @Named("mapToPayInfo")
  default List<SsgPointPayInfo> mapToPayInfo(BigDecimal payAmount) {
    return List.of(SsgPointPayInfo.builder()
        .payAmt(payAmount)
        .payGb("00")
        .payType("A00011")
        .build());
  }

  @Named("mapDupApprid")
  default String mapDupApprid(String dupApprid) {
    if(Strings.isEmpty(dupApprid)) {
      return null;
    }
    return dupApprid;
  }
}
