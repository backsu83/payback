package com.ebaykorea.payback.api.mapper;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.dto.event.CashEventRewardRequest;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.ReviewRewardRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CashEventRewardMapper {

  @Mapping(constant = "상품평 캐시백 적립", target = "comment")
  @Mapping(constant = "G8", target = "balanceCode")
  @Mapping(source = "eventType", target = "eventType")
  CashEventRewardRequest map(ReviewRewardRequestDto requestDto, EventType eventType);

  @Mapping(constant = "토스-신세계 유니버스 클럽 가입", target = "comment")
  @Mapping(constant = "G9", target = "balanceCode")
  @Mapping(source = "memberKey", target = "requestId")
  CashEventRewardRequest map(MemberEventRewardRequestDto requestDto, String memberKey);
}
