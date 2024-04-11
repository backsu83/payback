package com.ebaykorea.payback.schedulercluster.mapper;

import static com.ebaykorea.payback.schedulercluster.support.SchedulerUtils.orEmpty;
import static com.ebaykorea.payback.schedulercluster.support.TimeUtils.LOCAL_DATE_TIME_FORMATTER;

import com.ebaykorea.payback.schedulercluster.model.MassSaveEvent;
import com.ebaykorea.payback.schedulercluster.repository.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.schedulercluster.repository.stardb.entity.SmileCashEventEntity;
import java.sql.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MassSaveEventMapper {


  @Mapping(constant = "IAC", target = "subShopId")
  @Mapping(constant = "S002", target = "shopId")
  @Mapping(constant = "3", target = "smileCash.cashCode")
  @Mapping(source = "seqNo", target = "seqNo")
  @Mapping(source = "txId", target = "shopTransactionId")
  @Mapping(source = "bizKey", target = "shopOrderId")
  @Mapping(source = "auctionSmileCashEventType.promotionId", target = "promotionId")
  @Mapping(source = "expireDate", target = "smileCash.expirationDate", qualifiedByName = "mapExpirationFormatString")
  @Mapping(source = "saveAmount", target = "smileCash.amount")
  @Mapping(source = "auctionSmileCashEventType", target = "smileCash.shopManageCode")
  @Mapping(source = "source", target = "smileCash.shopComment", qualifiedByName = "mapShopComment")
  @Mapping(source = "saveStatus", target = "status")
  @Mapping(source = "memberId", target = "memberKey")
  @Mapping(source = "insertOperator", target = "operator")
  MassSaveEvent map(SmileCashSaveQueueEntity source);

  @Mapping(constant = "GMKT", target = "subShopId")
  @Mapping(constant = "3", target = "smileCash.cashCode")
  @Mapping(source = "smilePayNo", target = "shopTransactionId")
  @Mapping(expression = "java(source.getShopOrderId())", target = "shopOrderId")
  @Mapping(source = "ersNo", target = "promotionId")
  @Mapping(source = "expireDate", target = "smileCash.expirationDate", qualifiedByName = "mapExpirationFormatString")
  @Mapping(source = "requestMoney", target = "smileCash.amount")
  @Mapping(source = "cashBalanceType", target = "smileCash.shopManageCode")
  @Mapping(source = "comments", target = "smileCash.shopComment")
  @Mapping(source = "approvalStatus", target = "status")
  @Mapping(source = "custNo", target = "memberKey")
  @Mapping(source = "regId", target = "operator")
  MassSaveEvent map(SmileCashEventEntity source);

  @Named("mapExpirationFormatString")
  default String mapExpirationFormatString(final Timestamp expirationDate) {
    return LOCAL_DATE_TIME_FORMATTER.format(expirationDate.toInstant());
  }

  @Named("mapShopComment")
  default String mapShopComment(final SmileCashSaveQueueEntity entity) {
    return String.format("%s %s", orEmpty(entity.getReasonComment()), orEmpty(entity.getAdditionalReasonComment()));
  }

}
