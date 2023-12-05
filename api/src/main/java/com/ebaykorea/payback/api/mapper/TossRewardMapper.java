package com.ebaykorea.payback.api.mapper;


import com.ebaykorea.payback.api.dto.toss.TossRewardRequestDetailDto;
import com.ebaykorea.payback.api.dto.toss.TossRewardRequestDto;
import com.ebaykorea.payback.api.dto.toss.TossRewardResponseDto;
import com.ebaykorea.payback.api.dto.toss.TossRewardResultRequestDto;
import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.dto.event.toss.TossEventRewardRequestDetailDto;
import com.ebaykorea.payback.core.dto.event.toss.TossEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.toss.TossEventRewardResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = EventType.class
)
public interface TossRewardMapper {

  @Mapping(expression = "java(EventType.Toss)", target = "eventType")
  @Mapping(source = "amount", target = "saveAmount")
  @Mapping(source = "source.transactions", target = "details")
  TossEventRewardRequestDto map(TossRewardRequestDto source);

  @Mapping(expression = "java(EventType.Toss)", target = "eventType")
  TossEventRewardRequestDto map(TossRewardResultRequestDto source);

  @Mapping(source = "id", target = "detailId")
  @Mapping(source = "amount", target = "eventAmount")
  @Mapping(source = "transactAt", target = "eventDate")
  TossEventRewardRequestDetailDto map(TossRewardRequestDetailDto source);

  @Mapping(source = "smilePayNo", target = "transactionId")
  TossRewardResponseDto map(TossEventRewardResponseDto source);
}
