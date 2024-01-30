package com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper;

import com.ebaykorea.payback.core.domain.constant.SaveIntegrationType;
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEventResult;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity;
import java.sql.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = Timestamp.class
)
public interface SmileCashSaveQueueEntityMapper {

  @Mapping(source = "txId", target = "txId")
  @Mapping(source = "smileCashEvent.memberKey", target = "memberId")
  @Mapping(source = "smileCashEvent.eventType.auctionCode", target = "reasonCode")
  @Mapping(source = "reasonComment", target = "reasonComment")
  @Mapping(source = "reasonComment", target = "additionalReasonComment")
  @Mapping(constant = "9", target = "bizType")
  @Mapping(source = "smileCashEvent.requestNo", target = "bizKey")
  @Mapping(constant = "2", target = "smileCashType")
  @Mapping(expression = "java(Timestamp.from(smileCashEvent.getExpirationDate()))", target = "expireDate")
  @Mapping(source = "smileCashEvent.memberKey", target = "insertOperator")
  @Mapping(source = "smileCashEvent.eventNo", target = "referenceKey")
  @Mapping(source = "smileCashEvent.saveIntegrationType", target = "saveStatus", qualifiedByName = "mapRequestStatus")
  SmileCashSaveQueueEntity map(Long txId, String reasonComment, SmileCashEvent smileCashEvent);

  @Named("mapRequestStatus")
  default int mapRequestStatus(final SaveIntegrationType saveIntegrationType) {
    return saveIntegrationType == SaveIntegrationType.Mass ? 3 : 0;
  }

  @Mapping(source = "txId", target = "savingNo")
  EventRewardResultDto map(Long requestNo, Integer resultCode, Long txId);

  @Mapping(source = "txId", target = "smilePayNo")
  @Mapping(expression = "java(source.getSaveStatus() == 1)", target = "saved")
  @Mapping(expression = "java(source.getSaveStatus() == 2)", target = "failed")
  SmileCashEventResult map(SmileCashSaveQueueEntity source);
}
