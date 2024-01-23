package com.ebaykorea.payback.scheduler.mapper;

import com.ebaykorea.payback.scheduler.client.dto.smilecash.SaveResultDto;
import com.ebaykorea.payback.scheduler.repository.maindb2ex.entity.SmileCashSaveApprovalEntity;
import com.ebaykorea.payback.scheduler.repository.maindb2ex.entity.SmileCashSaveQueueEntity;
import java.sql.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SmileCashSaveMapper {

  @Mapping(source = "response.shopTransactionId", target = "txId")
  @Mapping(source = "response.authorizationId", target = "smileCashTxId")
  @Mapping(constant = "3", target = "txnType")
  @Mapping(expression = "java(fromString(response.getSmileCash().getTransactionDate()))", target = "transactionDate")
  @Mapping(source = "response.smileCash.amount", target = "saveAmount")
  @Mapping(expression = "java(fromString(response.getSmileCash().getTransactionDate()))", target = "diffProcBaseDate")
  @Mapping(constant = "1", target = "diffProcIs")
  @Mapping(source = "response.smileCash.shopManageCode", target = "reasonCode")
  @Mapping(source = "response.smileCash.shopComment", target = "reasonComment")
  @Mapping(constant = "payback-scheduler", target = "insertOperator")
  SmileCashSaveApprovalEntity map(SaveResultDto response, String smileUserKey, SmileCashSaveQueueEntity entity);

  default Timestamp fromString(final String date) {
    return Timestamp.valueOf(date);
  }
}
