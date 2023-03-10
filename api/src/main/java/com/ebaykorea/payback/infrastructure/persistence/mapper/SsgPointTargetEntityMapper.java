package com.ebaykorea.payback.infrastructure.persistence.mapper;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.infrastructure.persistence.mapper.dto.SsgPointTargetResponseDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SsgPointTargetEntityMapper {

  @Mapping(source = "unit.orderNo", target = "orderNo")
  @Mapping(source = "point.orderSiteType.shortCode", target = "siteType")
  @Mapping(source = "unit.pointTradeType.code", target = "tradeType")
  @Mapping(expression = "java(unit.getTransactionNo())", target = "trcNo")
  @Mapping(expression = "java(unit.getTradeNo())", target = "tradeNo")
  @Mapping(expression = "java(unit.getReceiptNo(mapToOrderSiteType()))", target = "receiptNo")
  @Mapping(source = "unit.scheduleDate", target = "scheduleDate")
  @Mapping(source = "unit.pointStatus.code", target = "pointStatus")
  @Mapping(source = "unit.payAmount", target = "payAmount")
  @Mapping(source = "unit.saveAmount", target = "saveAmount")
  @Mapping(source = "unit.pointToken", target = "pointToken")
  SsgPointTargetEntity map(SsgPoint point, SsgPointUnit unit);

  SsgPointTargetResponseDto mapToSsgTarget(SsgPointTargetEntity entity);

  default String mapToOrderSiteType() {
    return OrderSiteType.Gmarket.getTicker();
  }
}
