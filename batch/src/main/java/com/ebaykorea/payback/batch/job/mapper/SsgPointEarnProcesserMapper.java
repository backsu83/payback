package com.ebaykorea.payback.batch.job.mapper;

import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointEarnRequest;
import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointPayInfo;
import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointCommonResponse;
import com.ebaykorea.payback.batch.domain.SsgPointCertifier;
import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto;
import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import com.ebaykorea.payback.batch.util.PaybackDecimals;
import com.ebaykorea.payback.batch.util.PaybackInstants;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;
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
public interface SsgPointEarnProcesserMapper {

  @Mapping(source = "authInfo.clientId", target = "clientId")
  @Mapping(source = "authInfo.apiKey", target = "apiKey")
  @Mapping(source = "authInfo.branchId", target = "brchId")
  @Mapping(source = "tokenId", target = "tokenId")
  @Mapping(source = "processerDto.trcNo", target = "reqTrcNo")
  @Mapping(source = "processerDto.receiptNo", target = "recptNo")
  @Mapping(source = "processerDto.payAmount", target = "totAmt")
  @Mapping(source = "processerDto.tradeNo", target = "tradeNo")
  @Mapping(constant = "APITRN0121", target = "msgText")
  @Mapping(constant = "200020", target = "tradeGbCd")
  @Mapping(expression = "java(PaybackInstants.getDateTimeFormatBy(\"yyyyMMdd\"))", target = "busiDt")
  @Mapping(expression = "java(PaybackInstants.getDateTimeFormatBy(\"MMdd\"))", target = "tradeGentdDt")
  @Mapping(expression = "java(PaybackInstants.getDateTimeFormatBy(\"HHmmss\"))", target = "tradeGentdTm")
  @Mapping(constant = "0000", target = "tradeGentdStcd")
  @Mapping(constant = "0000", target = "tradeGentdPosno")
  @Mapping(constant = "000000", target = "doByid")
  @Mapping(constant = "O", target = "inputFlg")
  @Mapping(source = "cardNo", target = "cardNo")
  @Mapping(constant = "0000", target = "recptSeq")
  @Mapping(expression = "java(BigDecimal.valueOf(0L))", target = "pntNoAddProdAmt")
  @Mapping(source = "processerDto.orderNo", target = "orgSaleTradeNo")
  @Mapping(source = "processerDto.payAmount" , target = "payInfo" , qualifiedByName = "mapToPayInfo")
  SsgPointEarnRequest mapToRequest(SsgPointProcesserDto processerDto , SsgPointCertifier authInfo , String tokenId , String cardNo);

  @Named("mapToPayInfo")
  default List<SsgPointPayInfo> mapToPayInfo(BigDecimal payAmount) {
    return Lists.newArrayList(SsgPointPayInfo.builder()
        .payAmt(payAmount)
        .payGb("00")
        .payType("A00011")
        .build());
  }

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
  SsgPointTargetDto mapToTarget(SsgPointEarnRequest request , SsgPointCommonResponse response ,SsgPointProcesserDto processerDto);

}
