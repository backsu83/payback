package com.ebaykorea.payback.infrastructure.query.mapper;

import com.ebaykorea.payback.infrastructure.query.data.SmilePointTradeQueryData;
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
public interface SmilePointTradeQueryDataMapper {

  @Mapping(source = "smilePointTradeEntity.custNo", target = "buyerNo")
  @Mapping(source = "smilePointTradeEntity.errorMassage", target = "errorMsg")
  @Mapping(source = "smilePointTradeEntity.returnExpireDate", target = "expireDate")
  @Mapping(source = "smilePointTradeEntity.regDt", target = "regDate")
  @Mapping(source = "smilePointTradeEntity.serviceType", target = "saveType")
  SmilePointTradeQueryData map(SmilePointTradeEntity smilePointTradeEntity);

  default List<SmilePointTradeQueryData> map(final List<SmilePointTradeEntity> smilePointTradeEntityList) {
    return smilePointTradeEntityList.stream()
            .map(this::map)
            .collect(Collectors.toUnmodifiableList());
  }
}
