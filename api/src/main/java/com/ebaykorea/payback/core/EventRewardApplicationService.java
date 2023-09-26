package com.ebaykorea.payback.core;

import com.ebaykorea.payback.core.domain.constant.EventRequestStatusType;
import com.ebaykorea.payback.core.dto.event.*;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.core.gateway.UserGateway;
import com.ebaykorea.payback.core.repository.EventRewardRepository;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import com.ebaykorea.payback.util.PaybackStrings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.ebaykorea.payback.util.PaybackCollections.orEmptyStream;
import static com.ebaykorea.payback.util.PaybackStrings.isBlank;

@Service
@RequiredArgsConstructor
public class EventRewardApplicationService {
  private final EventRewardRepository eventRewardRepository;
  private final SmileCashEventRepository smileCashEventRepository;
  private final UserGateway userGateway;

  private static final String SMILE_CASH = "SMILE_CASH";

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String DUPLICATED = "ALREADY_PROCESSED";


  public EventRewardResponseDto saveEventReward(final EventRewardRequestDto request) {
    final var alreadySaved = eventRewardRepository.alreadySaved(request.getRequestId(), request.getEventType());
    if (alreadySaved) {
      return buildEventRewardResponse(request.getRequestId(), PaybackStrings.EMPTY, DUPLICATED);
    }

    final var eventRequestNo = eventRewardRepository.save(request);
    final var userId = userGateway.getUserId(request.getUserToken());

    final var memberEventRewardRequest = buildMemberEventRequest(eventRequestNo, request);

    return smileCashEventRepository.save(userId, memberEventRewardRequest)
        .map(this::getSaveProcessId)
        .map(saveProcessId -> {
          //적립 요청 상태 저장
          eventRewardRepository.saveStatus(request.getRequestId(), request.getEventType(), EventRequestStatusType.getStatusBySaveProcessId(saveProcessId));

          final var resultCode = isBlank(saveProcessId) ? FAILED : SUCCESS;
          return buildEventRewardResponse(request.getRequestId(), saveProcessId, resultCode);
        })
        .orElse(buildEventRewardResponse(request.getRequestId(), PaybackStrings.EMPTY, FAILED));
  }

  private MemberEventRewardRequestDto buildMemberEventRequest(final long eventRequestNo, final EventRewardRequestDto request) {
    return MemberEventRewardRequestDto.builder()
        .requestNo(eventRequestNo)
        .eventType(request.getEventType())
        .saveAmount(request.getTotalSaveAmount())
        .build();
  }

  private EventRewardResponseDto buildEventRewardResponse(final String requestId, final String saveProcessId, final String resultCode) {
    return EventRewardResponseDto.builder()
        .requestId(requestId)
        .saveProcessId(saveProcessId)
        .saveProcessType(SMILE_CASH)
        .resultCode(resultCode)
        .build();
  }

  private String getSaveProcessId(final MemberEventRewardResultDto memberEventRewardResult) {
    return Optional.ofNullable(memberEventRewardResult)
        .map(MemberEventRewardResultDto::getSmilePayNo)
        .map(String::valueOf)
        .orElse(PaybackStrings.EMPTY);
  }
}
