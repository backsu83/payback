package com.ebaykorea.payback.schedulercluster.mapper;

import static com.ebaykorea.payback.schedulercluster.support.SchedulerUtils.orEmpty;
import static com.ebaykorea.payback.schedulercluster.support.TimeUtils.LOCAL_DATE_TIME_FORMATTER;

import com.ebaykorea.payback.schedulercluster.client.dto.smilecash.MassSaveRequestDto;
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
public interface MassSaveMapper {

  @Mapping(source = "txId", target = "shopTransactionId")
  @Mapping(constant = "S002", target = "shopId")
  @Mapping(constant = "IAC", target = "subShopId")
  @Mapping(source = "bizKey", target = "shopOrderId")
  @Mapping(source = "auctionSmileCashEventType.promotionId", target = "promotionId")
  @Mapping(source = "expireDate", target = "smileCash.expirationDate", qualifiedByName = "mapExpirationFormatString")
  @Mapping(constant = "3", target = "smileCash.cashCode")
  @Mapping(source = "saveAmount", target = "smileCash.amount")
  @Mapping(source = "auctionSmileCashEventType", target = "smileCash.shopManageCode")
  @Mapping(source = "source", target = "smileCash.shopComment", qualifiedByName = "mapShopComment")
  MassSaveRequestDto map(SmileCashSaveQueueEntity source);

  @Mapping(source = "smilePayNo", target = "shopTransactionId")
  @Mapping(constant = "GMKT", target = "subShopId")
  @Mapping(expression = "java(source.getShopOrderId())", target = "shopOrderId")
  @Mapping(source = "ersNo", target = "promotionId")
  @Mapping(source = "expireDate", target = "smileCash.expirationDate", qualifiedByName = "mapExpirationFormatString")
  @Mapping(constant = "3", target = "smileCash.cashCode")
  @Mapping(source = "requestMoney", target = "smileCash.amount")
  @Mapping(source = "cashBalanceType", target = "smileCash.shopManageCode")
  @Mapping(source = "comments", target = "smileCash.shopComment")
  MassSaveRequestDto map(SmileCashEventEntity source);

  @Named("mapExpirationFormatString")
  default String mapExpirationFormatString(final Timestamp expirationDate) {
    return LOCAL_DATE_TIME_FORMATTER.format(expirationDate.toInstant());
  }

  @Named("mapShopComment")
  default String mapShopComment(final SmileCashSaveQueueEntity entity) {
    return String.format("%s %s", orEmpty(entity.getReasonComment()), orEmpty(entity.getAdditionalReasonComment()));
  }

}
