package com.ebaykorea.payback.infrastructure.persistence.mapper;

import com.ebaykorea.payback.core.domain.entity.smilepoint.SmilePointTrade;
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

  @Mapping(source = "smilePointTradeEntity.comment", target = "comment")
  @Mapping(source = "smilePointTradeEntity.point", target = "point")
  @Mapping(source = "smilePointTradeEntity.apprStatus", target = "apprStatus")
  @Mapping(source = "smilePointTradeEntity.custNo", target = "buyerNo")
  @Mapping(source = "smilePointTradeEntity.contrNo", target = "contrNo")
  @Mapping(source = "smilePointTradeEntity.errorMassage", target = "errorMsg")
  @Mapping(source = "smilePointTradeEntity.returnExpireDate", target = "expireDate")
  @Mapping(source = "smilePointTradeEntity.regDt", target = "regDate")
  @Mapping(source = "smilePointTradeEntity.reasonCode", target = "reasonCode")
  @Mapping(source = "smilePointTradeEntity.smilePayNo", target = "smilePayNo")
  @Mapping(source = "smilePointTradeEntity.targetType", target = "targetType")
  @Mapping(source = "smilePointTradeEntity.serviceType", target = "saveType")
  @Mapping(source = "smilePointTradeEntity.certApprId", target = "certApprId")
  @Mapping(source = "smilePointTradeEntity.userKey", target = "userKey")
  SmilePointTrade map(SmilePointTradeEntity smilePointTradeEntity);

  default List<SmilePointTrade> map(final List<SmilePointTradeEntity> smilePointTradeEntityList) {
    return smilePointTradeEntityList.stream()
            .map(smilePointTradeEntity -> map(smilePointTradeEntity))
            .collect(Collectors.toUnmodifiableList());
  }
}
