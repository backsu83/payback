package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.ssgpoint.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.util.PaybackInstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {
        PaybackInstants.class,
        OrderSiteType.class
    }
)
public interface SsgPointTargetEntityMapper {

  @Mapping(source = "point.buyerNo", target = "buyerId")
  @Mapping(expression = "java(unit.getTransactionNo())", target = "trcNo")
  @Mapping(expression = "java(unit.getTradeNo())", target = "tradeNo")
  @Mapping(expression = "java(unit.getReceiptNo(point.getOrderSiteType().getTicker() , point.getOrderDate()))", target = "receiptNo")
  @Mapping(source = "point.orderSiteType.shortCode", target = "siteType")
  @Mapping(source = "unit.pointStatus.statusType.code", target = "pointStatus")
  @Mapping(source = "unit.pointStatus.tradeType.code", target = "tradeType")
  @Mapping(source = "unit.pointOrigin.orgReceiptNo", target = "orgReceiptNo")
  @Mapping(source = "unit.pointOrigin.orgApproveId", target = "orgPntApprId")
  @Mapping(constant = "N", target = "cancelYn")
  @Mapping(constant = "N", target = "adminCancelYn")
  @Mapping(constant = "0L" , target = "tryCount")
  @Mapping(source = "unit.adminId" , target = "manualOprt")
  SsgPointTargetEntity map(SsgPoint point, SsgPointUnit unit);

  @Mapping(source = "insertOperator" , target = "adminId")
  @Mapping(expression = "java(OrderSiteType.forValue(entity.getSiteType()))" , target = "siteType")
  SsgPointTarget mapToSsgTarget(SsgPointTargetEntity entity);
}
