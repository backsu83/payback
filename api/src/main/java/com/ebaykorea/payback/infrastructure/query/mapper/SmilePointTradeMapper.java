package com.ebaykorea.payback.infrastructure.query.mapper;

import com.ebaykorea.payback.infrastructure.query.data.SmilePointTradeQueryResult;
import com.ebaykorea.payback.infrastructure.persistence.repository.customer.entity.SmilePointTradeEntity;
import com.ebaykorea.payback.util.PaybackTimestamps;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {PaybackTimestamps.class}
)
public interface SmilePointTradeMapper {

  @Mapping(source = "custNo", target = "buyerNo")
  @Mapping(source = "errorMassage", target = "errorMsg")
  @Mapping(source = "returnExpireDate", target = "expireDate")
  @Mapping(source = "regDt", target = "regDate")
  @Mapping(source = "serviceType", target = "saveType")
  SmilePointTradeQueryResult map(SmilePointTradeEntity smilePointTradeEntity);

  default List<SmilePointTradeQueryResult> map(final List<SmilePointTradeEntity> smilePointTradeEntityList) {
    return smilePointTradeEntityList.stream()
            .map(this::map)
            .collect(Collectors.toUnmodifiableList());
  }
}
