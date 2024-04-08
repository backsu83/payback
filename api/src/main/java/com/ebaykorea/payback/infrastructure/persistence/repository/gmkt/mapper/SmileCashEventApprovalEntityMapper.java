package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper;

import com.ebaykorea.payback.core.domain.entity.event.approval.ApprovalEventReward;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
import java.sql.Timestamp;
import java.time.Instant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SmileCashEventApprovalEntityMapper {

  @Mapping(source = "savingNo", target = "smilePayNo")
  @Mapping(constant = "50", target = "status")
  @Mapping(constant = "0000", target = "returnCode")
  @Mapping(source = "authorizationId", target = "certApprId")
  @Mapping(source = "saveAmount", target = "resultOutputImpbMoney")
  @Mapping(expression = "java(from(source.getExpireDate()))", target = "retExpireDate")
  @Mapping(expression = "java(from(source.getTransactionDate()))", target = "saveDate")
  SmileCashEventEntity map(ApprovalEventReward source);

  default Timestamp from(final Instant instant) {
    return Timestamp.from(instant);
  }
}
