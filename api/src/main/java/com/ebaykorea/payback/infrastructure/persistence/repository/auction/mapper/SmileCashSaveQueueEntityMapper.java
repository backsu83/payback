package com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper;

import com.ebaykorea.payback.core.dto.member.MemberCashbackRequestDto;
import com.ebaykorea.payback.core.dto.member.MemberCashbackResultDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.util.PaybackInstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.sql.Timestamp;

import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SmileCashSaveQueueEntityMapper {

  @Mapping(source = "txId", target = "txId")
  @Mapping(source = "memberKey", target = "memberId")
  @Mapping(constant = "RM02Y", target = "reasonCode")
  @Mapping(constant = "토스-신세계유니버스 가입 축하금 적립", target = "reasonComment")
  @Mapping(constant = "토스-신세계유니버스 가입 축하금 적립", target = "additionalReasonComment")
  @Mapping(constant = "9", target = "bizType")
  @Mapping(source = "request.requestNo", target = "bizKey")
  @Mapping(constant = "2", target = "smileCashType")
  @Mapping(source = "request.saveAmount", target = "saveAmount")
  @Mapping(expression = "java(getExpireDate())", target = "expireDate")
  @Mapping(source = "memberKey", target = "insertOperator")
  SmileCashSaveQueueEntity map(Long txId, String memberKey, MemberCashbackRequestDto request);

  default Timestamp getExpireDate() {
    return Timestamp.from(getDefaultEnableDate(PaybackInstants.now()));
  }

  @Mapping(source = "txId", target = "smilePayNo")
  MemberCashbackResultDto map(Long requestNo, Integer resultCode, Long txId);
}
