package com.ebaykorea.payback.api.mapper;

import static com.ebaykorea.payback.util.PaybackDateTimeFormatters.DATE_FORMATTER;
import static com.ebaykorea.payback.util.PaybackDateTimeFormatters.DATE_TIME_FORMATTER;

import com.ebaykorea.payback.api.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.api.dto.event.TossEventRewardRequestDto;
import com.ebaykorea.payback.core.domain.entity.event.approval.ApprovalEventReward;
import com.ebaykorea.payback.core.domain.entity.event.request.EventPlatform;
import com.ebaykorea.payback.core.domain.entity.event.request.Toss;
import com.ebaykorea.payback.core.dto.event.ApprovalEventRewardRequestDto;
import java.time.Instant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventRewardMapper {

  @Mapping(constant = "90", target = "expirationPeriod")
  EventPlatform map(EventRewardRequestDto source);

  default Toss map(TossEventRewardRequestDto source) {
    return Toss.of(source.getRequestNo(), source.getMemberKey(), source.getSaveAmount());
  }

  @Mapping(source = "request.totalApprovalAmount", target = "saveAmount")
  @Mapping(expression = "java(fromDateTimeString(request.getTransactionDate()))", target = "transactionDate")
  @Mapping(expression = "java(fromDateString(request.getExpireDate()))", target = "expireDate")
  ApprovalEventReward map(Long savingNo, ApprovalEventRewardRequestDto request);

  default Instant fromDateString(final String dateString) {
    return DATE_FORMATTER.parse(dateString, Instant::from);
  }

  default Instant fromDateTimeString(final String dateTimeString) {
    return DATE_TIME_FORMATTER.parse(dateTimeString, Instant::from);
  }
}
