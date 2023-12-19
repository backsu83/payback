package com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper;

import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate;

import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.CashEventRewardRequest;
import com.ebaykorea.payback.core.dto.event.CashEventRewardResult;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.util.PaybackInstants;
import java.sql.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SmileCashSaveQueueEntityMapper {

  @Mapping(source = "txId", target = "txId")
  @Mapping(source = "request.requestId", target = "memberId")
  @Mapping(expression = "java(request.getEventType().getAuctionCode())", target = "reasonCode")
  @Mapping(source = "request.comment", target = "reasonComment")
  @Mapping(source = "request.comment", target = "additionalReasonComment")
  @Mapping(constant = "9", target = "bizType")
  @Mapping(source = "request.requestNo", target = "bizKey")
  @Mapping(constant = "2", target = "smileCashType")
  @Mapping(source = "request.saveAmount", target = "saveAmount")
  @Mapping(expression = "java(getExpireDate())", target = "expireDate")
  @Mapping(source = "request.requestId", target = "insertOperator")
  SmileCashSaveQueueEntity map(Long txId, CashEventRewardRequest request);

  @Mapping(source = "request.status", target = "saveStatus")
  @Mapping(source = "request.tryCount", target = "retryCount")
  @Mapping(source = "request.operator", target = "insertOperator")
  SmileCashSaveQueueEntity map(Long seqNo, SetEventRewardRequestDto request);

  default Timestamp getExpireDate() {
    return Timestamp.from(getDefaultEnableDate(PaybackInstants.now()));
  }

  @Mapping(source = "txId", target = "smilePayNo")
  CashEventRewardResult map(Long requestNo, Integer resultCode, Long txId);

  @Mapping(source = "txId", target = "smilePayNo")
  @Mapping(expression = "java(source.getSaveStatus() == 1)", target = "saved")
  @Mapping(expression = "java(source.getSaveStatus() == 2)", target = "failed")
  SmileCashEvent map(SmileCashSaveQueueEntity source);
}
