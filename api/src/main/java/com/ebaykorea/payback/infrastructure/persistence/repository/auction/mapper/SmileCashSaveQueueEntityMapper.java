package com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper;

import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.util.PaybackInstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SmileCashSaveQueueEntityMapper {

  @Mapping(source = "txId", target = "txId")
  @Mapping(source = "request.memberKey", target = "memberId")
  @Mapping(source = "request.eventType.auctionCode", target = "reasonCode")
  @Mapping(source = "reasonComment", target = "reasonComment")
  @Mapping(source = "reasonComment", target = "additionalReasonComment")
  @Mapping(constant = "9", target = "bizType")
  @Mapping(source = "request.requestNo", target = "bizKey")
  @Mapping(constant = "2", target = "smileCashType")
  @Mapping(expression = "java(getExpireDate(request.getExpirationDate()))", target = "expireDate")
  @Mapping(source = "request.memberKey", target = "insertOperator")
  @Mapping(source = "request.eventNo", target = "referenceKey")
  SmileCashSaveQueueEntity map(Long txId, String reasonComment, EventRewardRequestDto request);

  @Mapping(source = "request.status", target = "saveStatus")
  @Mapping(source = "request.tryCount", target = "retryCount")
  @Mapping(source = "request.operator", target = "insertOperator")
  SmileCashSaveQueueEntity map(Long seqNo, SetEventRewardRequestDto request);

  default Timestamp getExpireDate(final Instant expirationDate) {
    return Optional.ofNullable(expirationDate)
        .map(Timestamp::from)
        .orElse(Timestamp.from(getDefaultEnableDate(PaybackInstants.now())));
  }

  @Mapping(source = "txId", target = "smilePayNo")
  EventRewardResultDto map(Long requestNo, Integer resultCode, Long txId);

  @Mapping(source = "txId", target = "smilePayNo")
  @Mapping(expression = "java(source.getSaveStatus() == 1)", target = "saved")
  @Mapping(expression = "java(source.getSaveStatus() == 2)", target = "failed")
  SmileCashEvent map(SmileCashSaveQueueEntity source);
}
