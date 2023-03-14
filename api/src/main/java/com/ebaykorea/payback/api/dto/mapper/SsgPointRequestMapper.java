package com.ebaykorea.payback.api.dto.mapper;

import com.ebaykorea.payback.api.dto.SaveSsgPointRequest;
import com.ebaykorea.payback.core.dto.SaveSsgPointRequestDto;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring"
)
public interface SsgPointRequestMapper {
  SaveSsgPointRequestDto map(SaveSsgPointRequest request);
}
