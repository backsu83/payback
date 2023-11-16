package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper;

import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointOrderNoDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.ssgpoint.entity.SsgPointOrderNoEntity;
import com.ebaykorea.payback.util.PaybackInstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {PaybackInstants.class}
)
public interface SsgPointOrderNoEntityMapper {

  @Mapping(source = "orderNo", target = "orderNo")
  @Mapping(source = "siteType", target = "siteType")
  @Mapping(source = "insertDate", target = "insertDate")
  @Mapping(source = "updateDate", target = "updateDate")
  SsgPointOrderNoEntity map(SsgPointOrderNoDto SsgPointOrderNoDto);

}
