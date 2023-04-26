package com.ebaykorea.payback.infrastructure.persistence.mapper;

import com.ebaykorea.payback.core.dto.VerifyDailySsgPointDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointDailyVerifyEntity;
import com.ebaykorea.payback.util.PaybackInstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {PaybackInstants.class}
)
public interface SsgPointDailyVerifyEntityMapper {

  SsgPointDailyVerifyEntity map(VerifyDailySsgPointDto verifyDailySsgPointDto);
  VerifyDailySsgPointDto map(SsgPointDailyVerifyEntity verifyDailySsgPointDto);
}
