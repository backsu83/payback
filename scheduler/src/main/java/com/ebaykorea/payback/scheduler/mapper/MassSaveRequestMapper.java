package com.ebaykorea.payback.scheduler.mapper;

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
  @Mapping(constant = "GPR0002", target = "promotionId") //TODO
  @Mapping(source = "expireDate", target = "smileCash.expirationDate", qualifiedByName = "mapExpirationFormatString")
  @Mapping(constant = "3", target = "smileCash.cashCode")
  @Mapping(source = "saveAmount", target = "smileCash.amount")
  @Mapping(source = "reasonCode", target = "smileCash.shopManageCode")
  @Mapping(source = "reasonComment", target = "smileCash.shopComment")
  MassSaveRequestDto map(SmileCashSaveQueueEntity source);

  @Named("mapExpirationFormatString")
  default String mapExpirationFormatString(Timestamp expirationDate) {
    return LOCAL_DATE_TIME_FORMATTER.format(expirationDate.toInstant());
  }

  @Mapping(source = "txId", target = "shopTransactionId")
  SaveResultRequestDto mapToSaveResultRequest(SmileCashSaveQueueEntity source);
}
