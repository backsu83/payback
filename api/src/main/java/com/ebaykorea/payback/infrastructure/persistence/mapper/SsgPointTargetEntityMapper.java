package com.ebaykorea.payback.infrastructure.persistence.mapper;

import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.dto.SsgPointCancedDto;
import com.ebaykorea.payback.core.dto.SsgPointTargetResponseDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.util.PaybackInstants;
import com.ebaykorea.payback.util.PaybackTimestamps;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {PaybackInstants.class}
)
public interface SsgPointTargetEntityMapper {

  @Mapping(source = "unit.orderNo", target = "orderNo")
  @Mapping(expression = "java(unit.getTransactionNo())", target = "trcNo")
  @Mapping(expression = "java(unit.getTradeNo())", target = "tradeNo")
  @Mapping(expression = "java(unit.getReceiptNo(point.getOrderSiteType().getTicker()))", target = "receiptNo")
  @Mapping(source = "point.orderSiteType.shortCode", target = "siteType")
  @Mapping(source = "unit.pointStatus.pointStatusType.code", target = "pointStatus")
  @Mapping(source = "unit.pointStatus.pointTradeType.code", target = "tradeType")
  @Mapping(source = "unit.payAmount", target = "payAmount")
  @Mapping(source = "unit.saveAmount", target = "saveAmount")
  @Mapping(source = "unit.scheduleDate", target = "scheduleDate")
  @Mapping(source = "unit.pointOrigin.orgReceiptNo", target = "orgReceiptNo")
  @Mapping(source = "unit.pointOrigin.orgApproveId", target = "orgPntApprId")
  @Mapping(constant = "N", target = "adminCancelYn")
  @Mapping(constant = "0L" , target = "tryCount")
  SsgPointTargetEntity map(SsgPoint point, SsgPointUnit unit);

  SsgPointTargetResponseDto mapToSsgTarget(SsgPointTargetEntity entity);

  SsgPointCancedDto mapToPointCancel(SsgPointTargetEntity entity);
}
