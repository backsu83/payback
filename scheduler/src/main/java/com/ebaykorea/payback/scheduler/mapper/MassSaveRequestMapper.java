package com.ebaykorea.payback.scheduler.mapper;

import static com.ebaykorea.payback.scheduler.support.SchedulerUtils.orEmpty;
import static com.ebaykorea.payback.scheduler.support.TimeUtils.LOCAL_DATE_TIME_FORMATTER;

import com.ebaykorea.payback.scheduler.client.dto.smilecash.MassSaveRequestDto;
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SaveResultRequestDto;
import com.ebaykorea.payback.scheduler.repository.maindb2ex.entity.SmileCashSaveQueueEntity;
import java.sql.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MassSaveRequestMapper {
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

  @Named("mapExpirationFormatString")
  default String mapExpirationFormatString(final Timestamp expirationDate) {
    return LOCAL_DATE_TIME_FORMATTER.format(expirationDate.toInstant());
  }

  @Named("mapShopComment")
  default String mapShopComment(final SmileCashSaveQueueEntity entity) {
    return String.format("%s %s", orEmpty(entity.getReasonComment()), orEmpty(entity.getAdditionalReasonComment()));
  }

  @Mapping(source = "txId", target = "shopTransactionId")
  SaveResultRequestDto mapToSaveResultRequest(SmileCashSaveQueueEntity source);
}
