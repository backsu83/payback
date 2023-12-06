package com.ebaykorea.payback.scheduler.service;

import static com.ebaykorea.payback.scheduler.domain.constant.EventRequestStatusType.RequestFailed;

import com.ebaykorea.payback.scheduler.client.PaybackApiClient;
import com.ebaykorea.payback.scheduler.client.QuiltApi;
import com.ebaykorea.payback.scheduler.client.dto.member.QuiltBaseResponse;
import com.ebaykorea.payback.scheduler.client.dto.payback.CommonResponse;
import com.ebaykorea.payback.scheduler.client.dto.payback.EventRewardRequestDto;
import com.ebaykorea.payback.scheduler.client.dto.payback.EventRewardResultDto;
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

  public void run(final String tenantId, final String startDate, final String endDate) {
    repositoryCustom.findNotRequestedRequests(tenantId, startDate, endDate)
        .forEach(notRequested -> {
          try {
            final var buyerNo = getBuyerNo(notRequested.getUserToken());
            final var isSuccess = saveEventReward(buyerNo, notRequested);
            saveStatusIfNotExist(notRequested, EventRequestStatusType.getStatus(isSuccess));
          } catch (Exception ex) {
            log.info(String.format("exception occurred: notRequested: %s ex:%s", notRequested.toString(), ex.getMessage()));
            saveStatusIfNotExist(notRequested, RequestFailed);
          }
        });
  }

  private String getBuyerNo(final String userToken) {
    return quiltApi.findUserId(userToken)
        .flatMap(QuiltBaseResponse::findSuccessData)
        .orElseThrow(() -> new RuntimeException("buyerNo 없음"));
  }

  private boolean saveEventReward(final String buyerNo, final EventRewardRequestEntity notRequested) {
    log.info(String.format("saveEventReward: buyerNo: %s, notRequested: %s", buyerNo, notRequested.toString()));
    return paybackApiClient.saveEventRewardByMember(buildRequest(buyerNo, notRequested))
        .flatMap(CommonResponse::findSuccessData)
        .map(EventRewardResultDto::isSuccess)
        .orElse(false);
  }

  private void saveStatusIfNotExist(final EventRewardRequestEntity entity, final EventRequestStatusType eventRequestStatus) {
    final var hasStatus = entity.getStatuses().stream()
        .anyMatch(status -> status.getEventRequestStatus() == eventRequestStatus);
    if (hasStatus) {
      return;
    }
    log.info(String.format("status saved: requestNo: %d, status: %s", entity.getRequestNo(), eventRequestStatus.toString()));
    repository.save(buildStatusEntity(entity.getRequestNo(), eventRequestStatus));
  }

  private EventRewardRequestDto buildRequest(final String memberKey, final EventRewardRequestEntity entity) {
    return EventRewardRequestDto.builder()
        .requestNo(entity.getRequestNo())
        .memberKey(memberKey)
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
