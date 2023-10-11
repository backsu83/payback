package com.ebaykorea.payback.core;

import com.ebaykorea.payback.core.domain.constant.EventRequestStatusType;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResponseDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardResultDto;
import com.ebaykorea.payback.core.gateway.UserGateway;
import com.ebaykorea.payback.core.repository.EventRewardRepository;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import com.ebaykorea.payback.util.PaybackStrings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ebaykorea.payback.util.PaybackStrings.isBlank;

@Service
@RequiredArgsConstructor
public class EventRewardApplicationService {
  private final EventRewardRepository eventRewardRepository;
  private final SmileCashEventRepository smileCashEventRepository;
  private final UserGateway userGateway;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String DUPLICATED = "ALREADY_PROCESSED";

  public EventRewardResponseDto saveEventReward(final EventRewardRequestDto request) {
    final var alreadySaved = eventRewardRepository.alreadySaved(request.getRequestId(), request.getEventType());
    if (alreadySaved) {
      return buildEventRewardResponse(PaybackStrings.EMPTY, DUPLICATED);
    }

    final var eventRequestNo = eventRewardRepository.save(request);
    final var userId = userGateway.getUserId(request.getUserToken());

    final var memberEventRewardRequest = buildMemberEventRequest(eventRequestNo, request);

    return smileCashEventRepository.save(userId, memberEventRewardRequest)
        .map(this::getSmilePayNo)
        .map(smilePayNo -> {
          //적립 요청 상태 저장
          eventRewardRepository.saveStatus(request.getRequestId(), request.getEventType(), EventRequestStatusType.getStatusBySaveProcessId(smilePayNo));

          final var resultCode = isBlank(smilePayNo) ? FAILED : SUCCESS;
          return buildEventRewardResponse(smilePayNo, resultCode);
        })
        .orElse(buildEventRewardResponse(PaybackStrings.EMPTY, FAILED));
  }

  private MemberEventRewardRequestDto buildMemberEventRequest(final long eventRequestNo, final EventRewardRequestDto request) {
    return MemberEventRewardRequestDto.builder()
        .requestNo(eventRequestNo)
        .eventType(request.getEventType())
        .saveAmount(request.getSaveAmount())
        .build();
  }

  private EventRewardResponseDto buildEventRewardResponse(final String smilePayNo, final String resultCode) {
    return EventRewardResponseDto.builder()
        .smilePayNo(smilePayNo)
        .resultCode(resultCode)
        .build();
  }

  private String getSmilePayNo(final MemberEventRewardResultDto memberEventRewardResult) {
    return Optional.ofNullable(memberEventRewardResult)
        .map(MemberEventRewardResultDto::getSmilePayNo)
        .filter(smilePayNo -> smilePayNo > 0L)
        .map(String::valueOf)
        .orElse(PaybackStrings.EMPTY);
  }
}
