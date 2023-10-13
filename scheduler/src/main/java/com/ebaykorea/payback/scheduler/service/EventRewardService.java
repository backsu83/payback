package com.ebaykorea.payback.scheduler.service;

import com.ebaykorea.payback.scheduler.client.PaybackApiClient;
import com.ebaykorea.payback.scheduler.client.QuiltApi;
import com.ebaykorea.payback.scheduler.client.dto.member.QuiltBaseResponse;
import com.ebaykorea.payback.scheduler.client.dto.payback.CommonResponse;
import com.ebaykorea.payback.scheduler.client.dto.payback.MemberEventRewardRequestDto;
import com.ebaykorea.payback.scheduler.client.dto.payback.MemberEventRewardResponseDto;
import com.ebaykorea.payback.scheduler.domain.constant.EventRequestStatusType;
import com.ebaykorea.payback.scheduler.domain.constant.EventType;
import com.ebaykorea.payback.scheduler.repository.opayreward.EventRewardRepositoryCustom;
import com.ebaykorea.payback.scheduler.repository.opayreward.EventRewardRequestStatusRepository;
import com.ebaykorea.payback.scheduler.repository.opayreward.entity.event.EventRewardRequestEntity;
import com.ebaykorea.payback.scheduler.repository.opayreward.entity.event.EventRewardRequestStatusEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventRewardService {
  private final EventRewardRepositoryCustom repositoryCustom;
  private final EventRewardRequestStatusRepository repository;

  private final QuiltApi quiltApi;
  private final PaybackApiClient paybackApiClient;

  public void run(final String tenantId) {
    repositoryCustom.findNotRequestedRequests(tenantId)
        .forEach(notRequested -> {
          final var buyerNo = getBuyerNo(notRequested.getUserToken());
          final var isSuccess = saveEventReward(buyerNo, notRequested);
          repository.save(buildStatusEntity(notRequested.getRequestNo(), EventRequestStatusType.getStatus(isSuccess)));
        });
  }

  private String getBuyerNo(final String userToken) {
    return quiltApi.findUserId(userToken)
        .flatMap(QuiltBaseResponse::findSuccessData)
        .orElseThrow(() -> new RuntimeException("buyerNo 없음"));
  }

  private boolean saveEventReward(final String buyerNo, final EventRewardRequestEntity notRequested) {
    return paybackApiClient.saveEventRewardByMember(buyerNo, buildRequest(notRequested))
        .flatMap(CommonResponse::findSuccessData)
        .map(MemberEventRewardResponseDto::isSuccess)
        .orElse(false);
  }

  private MemberEventRewardRequestDto buildRequest(final EventRewardRequestEntity entity) {
    return MemberEventRewardRequestDto.builder()
        .requestNo(entity.getRequestNo())
        .saveAmount(entity.getSaveAmount())
        .eventType(EventType.Toss)
        .build();
  }

  private EventRewardRequestStatusEntity buildStatusEntity(final long requestNo, final EventRequestStatusType statusType) {
    return EventRewardRequestStatusEntity.builder()
        .requestNo(requestNo)
        .eventRequestStatus(statusType)
        .build();
  }
}
