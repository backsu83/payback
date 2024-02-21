package com.ebaykorea.payback.api.mapper;


import com.ebaykorea.payback.api.dto.toss.TossRewardResponseDto;
import com.ebaykorea.payback.api.dto.toss.TossRewardResultRequestDto;
import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.dto.event.toss.TossEventRewardResponseDto;
import com.ebaykorea.payback.core.dto.event.toss.TossRewardRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = EventType.class
)
public interface TossRewardMapper {
  TossRewardRequestDto map(TossRewardResultRequestDto source);

  @Mapping(source = "smilePayNo", target = "transactionId")
  TossRewardResponseDto map(TossEventRewardResponseDto source);
}
