package com.ebaykorea.payback.schedulercluster.mapper;

import com.ebaykorea.payback.schedulercluster.client.dto.smilecash.MassSaveRequestDto;
import com.ebaykorea.payback.schedulercluster.model.MassSaveEvent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MassSaveMapper {

  MassSaveRequestDto map(MassSaveEvent source);
}
