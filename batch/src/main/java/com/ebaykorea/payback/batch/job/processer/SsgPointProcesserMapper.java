package com.ebaykorea.payback.batch.job.processer;

import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointEarnRequest;
import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointPayInfo;
import com.ebaykorea.payback.batch.config.properties.SsgPointAuthProperties.Gmarket;
import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto;
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
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
        PaybackInstants.class
    }
)
public interface SsgPointProcesserMapper {

  @Mapping(expression = "java(OrderSiteType.forValue(entity.getSiteType()))", target = "siteType")
  @Mapping(expression = "java(PointTradeType.from(entity.getTradeType()))", target = "tradeType")
  @Mapping(expression = "java(PointStatusType.from(entity.getPointStatus()))", target = "status")
  SsgPointProcesserDto map(SsgPointTargetEntity entity);

  @Mapping(source = "properties.clientId", target = "clientId")
  @Mapping(source = "properties.apiKey", target = "apiKey")
  @Mapping(source = "properties.branchId", target = "brchId")
  @Mapping(source = "tokenId", target = "tokenId")
  @Mapping(source = "entity.trcNo", target = "reqTrcNo")
  @Mapping(source = "entity.receiptNo", target = "recptNo")
  @Mapping(source = "entity.payAmount", target = "totAmt")
  @Mapping(source = "entity.tradeNo", target = "tradeNo")
  @Mapping(constant = "APITRN0121", target = "msgText")
  @Mapping(constant = "200020", target = "tradeGbCd")
  @Mapping(expression = "java(PaybackInstants.getDateTimeForString(\"yyyyMMdd\"))", target = "busiDt")
  @Mapping(expression = "java(PaybackInstants.getDateTimeForString(\"MMdd\"))", target = "tradeGentdDt")
  @Mapping(expression = "java(PaybackInstants.getDateTimeForString(\"HHmmss\"))", target = "tradeGentdTm")
  @Mapping(constant = "0000", target = "tradeGentdStcd")
  @Mapping(constant = "0000", target = "tradeGentdPosno")
  @Mapping(constant = "000000", target = "doByid")
  @Mapping(constant = "O", target = "inputFlg")
  @Mapping(source = "cardNo", target = "cardNo")
  @Mapping(constant = "0000", target = "recptSeq")
  @Mapping(expression = "java(BigDecimal.valueOf(0L))", target = "pntNoAddProdAmt")
  @Mapping(expression = "java(PaybackInstants.getDateTimeForString(\"yyyyMMddHHmmss\"))", target = "orgSaleTradeNo")
  @Mapping(source = "entity.payAmount" , target = "payInfo" , qualifiedByName = "mapToPayInfo")
  SsgPointEarnRequest map(SsgPointProcesserDto entity , Gmarket properties , String tokenId , String cardNo);

  @Named("mapToPayInfo")
  default List<SsgPointPayInfo> mapToPayInfo(BigDecimal payAmount) {
    return Lists.newArrayList(SsgPointPayInfo.builder()
        .payAmt(payAmount)
        .payGb("00")
        .payType("A00011")
        .build());
  }

}
