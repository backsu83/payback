package com.ebaykorea.payback.api.mapper;


import com.ebaykorea.payback.api.dto.toss.TossEventRewardRequestDetailDto;
import com.ebaykorea.payback.api.dto.toss.TossEventRewardRequestDto;
import com.ebaykorea.payback.api.dto.toss.TossEventRewardResponseDto;
import com.ebaykorea.payback.api.dto.toss.TossEventRewardResultRequestDto;
import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDetailDto;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = EventType.class
)
public interface TossEventRewardMapper {

  @Mapping(expression = "java(EventType.Toss)", target = "eventType")
  @Mapping(source = "amount", target = "saveAmount")
  @Mapping(source = "source.transactions", target = "details")
  EventRewardRequestDto map(TossEventRewardRequestDto source);

  @Mapping(expression = "java(EventType.Toss)", target = "eventType")
  EventRewardRequestDto map(TossEventRewardResultRequestDto source);

  @Mapping(source = "id", target = "detailId")
  @Mapping(source = "amount", target = "eventAmount")
  @Mapping(source = "transactAt", target = "eventDate")
  EventRewardRequestDetailDto map(TossEventRewardRequestDetailDto source);

  @Mapping(source = "smilePayNo", target = "transactionId")
  TossEventRewardResponseDto map(EventRewardResponseDto source);
}
