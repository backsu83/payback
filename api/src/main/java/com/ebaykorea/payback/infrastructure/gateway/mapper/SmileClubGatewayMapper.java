package com.ebaykorea.payback.infrastructure.gateway.mapper;

import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointCard;
import com.ebaykorea.payback.infrastructure.gateway.client.smileclub.dto.SmileClubSsgPointResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SmileClubGatewayMapper {

  SsgPointCard map(SmileClubSsgPointResponseDto responseDto);
}
