package com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper;

import com.ebaykorea.payback.core.domain.entity.event.approval.ApprovalEventReward;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveApprovalEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity;
import java.sql.Timestamp;
import java.time.Instant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SmileCashSaveApprovalEntityMapper {

  @Mapping(source = "approval.savingNo", target = "txId")
  @Mapping(source = "approval.authorizationId", target = "smileCashTxId")
  @Mapping(constant = "3", target = "txnType")
  @Mapping(expression = "java(from(approval.getTransactionDate()))", target = "saveDate")
  @Mapping(source = "approval.saveAmount", target = "saveAmount")
  @Mapping(expression = "java(from(approval.getExpireDate()))", target = "expireDate")
  @Mapping(expression = "java(from(approval.getTransactionDate()))", target = "diffProcBaseDate")
  @Mapping(constant = "1", target = "diffProcIs")
  @Mapping(source = "approval.shopManageCode", target = "reasonCode")
  @Mapping(source = "approval.shopComment", target = "reasonComment")
  @Mapping(constant = "payback-api", target = "insertOperator")
  SmileCashSaveApprovalEntity map(ApprovalEventReward approval, SmileCashSaveQueueEntity entity);

  default Timestamp from(final Instant instant) {
    return Timestamp.from(instant);
  }
}
